package uk.ac.ebi.subs.repository.repos;

import com.mongodb.BulkWriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.ac.ebi.subs.processing.ProcessingCertificate;
import uk.ac.ebi.subs.processing.ProcessingCertificateEnvelope;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Make changes to many submittables at once
 */
@Component
public class SubmittablesBulkOperations {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MongoTemplate mongoTemplate;


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


}
