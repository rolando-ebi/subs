package uk.ac.ebi.subs.repository.repos;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.ac.ebi.subs.data.status.ProcessingStatus;
import uk.ac.ebi.subs.processing.ProcessingCertificate;
import uk.ac.ebi.subs.processing.ProcessingCertificateEnvelope;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

/**
 * Make changes to many submittables at once
 */
@Component
public class SubmittablesBulkOperations {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MongoTemplate mongoTemplate;


    public Page itemsByDomain(String domainName, Pageable pageable, Class clazz) {
        List resultsList = getLimitedItemListByDomain(domainName, pageable, clazz);
        long totalItemsCount = getTotalItemCountByDomain(domainName,clazz);
        return new PageImpl(resultsList,pageable,totalItemsCount);
    }

    private long getTotalItemCountByDomain(String domainName, Class clazz) {
        AggregationResults aggregationResults = mongoTemplate.aggregate(Aggregation.newAggregation(
                domainMatchOperation(domainName),
                groupByAlias(),
                count().as("count")
        ), clazz, clazz);

        Object results = aggregationResults.getRawResults().get("result");

        if (results != null && results instanceof BasicDBList) {
            BasicDBList resultsList = (BasicDBList) results;
            return ((BasicDBObject)resultsList.get(0)).getLong("count");
        }
        return -1;
    }

    private List getLimitedItemListByDomain(String domainName, Pageable pageable, Class clazz) {
        final List resultsList = new ArrayList();

        AggregationResults aggregationResults = mongoTemplate.aggregate(Aggregation.newAggregation(
                domainMatchOperation(domainName),
                sortAliasCreatedDate(),
                groupByAliasWithFirstItem(),
                skip((long)pageable.getOffset()),
                limit((long)pageable.getPageSize())
        ), clazz,clazz);

        /*
            TODO with a once we have mongo 3.4 db we can use ReplaceRootOperation replaceRootOp = replaceRoot("first");
            after limit to skip the awkward extraction step below

         */
        Object results = aggregationResults.getRawResults().get("result");

        if (results != null &&  results instanceof BasicDBList) {
            BasicDBList resultSet = (BasicDBList) results;
            resultSet.stream()
                    .map(o -> (DBObject) ((DBObject) o).get("first"))
                    .map(first -> mongoTemplate.getConverter().read(clazz, first))
                    .forEachOrdered(o -> resultsList.add(o));
        }
        return resultsList;
    }

    private GroupOperation groupByAliasWithFirstItem() {
        return group("alias").first("$$ROOT").as("first");
    }
    private GroupOperation groupByAlias() {
        return group("alias");
    }

    private SortOperation sortAliasCreatedDate() {
        return sort(Sort.Direction.DESC,"alias").and(Sort.Direction.DESC,"createdDate");
    }

    private MatchOperation domainMatchOperation(String domainName) {
        return match(where("domain.name").is(domainName));
    }

    public void updateProcessingStatusBySubmissionId(
            String submissionId,
            ProcessingStatus newStatus,
            ProcessingStatus currentStatus,
            Class submittableClass
    ) {
        Assert.notNull(submissionId);
        Assert.notNull(newStatus);
        Assert.notNull(currentStatus);
        Assert.notNull(submittableClass);

        Query query = query(where("submission.$id").is(submissionId).and("status").is(currentStatus.name()));
        Update update = update("status", newStatus.name());

        WriteResult writeResult = mongoTemplate.updateMulti(query, update, submittableClass);

        logger.info("Set status for {} in submission {}, changing statuses from {} to {}, changed {}",
                submittableClass,
                submissionId,
                currentStatus, newStatus,
                writeResult.getN());

    }

    public void applyProcessingCertificates(ProcessingCertificateEnvelope envelope, Class submittableClass) {
        Assert.notNull(envelope);
        Assert.notNull(envelope.getSubmissionId());
        Assert.notNull(envelope.getProcessingCertificates());

        BulkOperations ops = mongoTemplate.bulkOps(BulkOperations.BulkMode.UNORDERED, submittableClass);


        for (ProcessingCertificate certificate : envelope.getProcessingCertificates()) {
            Query query = query(
                    where("_id").is(certificate.getSubmittableId())
                            .and("submission.$id").is(envelope.getSubmissionId())
            );

            Update update = new Update();
            boolean haveUpdates = false;

            if (certificate.getAccession() != null) {
                update.set("accession", certificate.getAccession());
                haveUpdates = true;
            }
            if (certificate.getProcessingStatus() != null) {
                update.set("status", certificate.getProcessingStatus().name());
                haveUpdates = true;
            }

            if (haveUpdates) {
                ops.updateOne(query, update);
            }

        }


        BulkWriteResult writeResult = ops.execute();


        logger.info("Applying certs for {} in submission {}, {} certs, changed {}",
                submittableClass,
                envelope.getSubmissionId(),
                envelope.getProcessingCertificates().size(),
                writeResult.getModifiedCount()
        );

    }

    public void deleteSubmissionContents(String submissionId, Class submittableClass){
        Query query = query(where("submission.$id").is(submissionId));

        WriteResult writeResult = mongoTemplate.remove(query,submittableClass);

        logger.info("Removing documents for {} in submission {}, removed {}",
                submittableClass,
                submissionId,
                writeResult.getN()
        );
    }


}
