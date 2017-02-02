package uk.ac.ebi.subs.repository.submittable;

import com.mongodb.BulkWriteResult;
import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.ac.ebi.subs.data.status.ProcessingStatus;
import uk.ac.ebi.subs.processing.ProcessingCertificate;
import uk.ac.ebi.subs.processing.ProcessingCertificateEnvelope;

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

        Query query = query(where("submissionId").is(submissionId).and("status").is(currentStatus.name()));
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
                            .and("submissionId").is(envelope.getSubmissionId())
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
        Query query = query(where("submissionId").is(submissionId));

        WriteResult writeResult = mongoTemplate.remove(query,submittableClass);

        logger.info("Removing documents for {} in submission {}, removed {}",
                submittableClass,
                submissionId,
                writeResult.getN()
        );
    }


}
