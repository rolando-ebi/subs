package uk.ac.ebi.subs.repository.submittable;

import com.mongodb.WriteResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;


import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.ac.ebi.subs.data.status.ProcessingStatus;
import uk.ac.ebi.subs.data.submittable.Submittable;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Update.*;
import static org.springframework.data.mongodb.core.query.Query.*;
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
    ){
        Assert.notNull(submissionId);
        Assert.notNull(newStatus);
        Assert.notNull(currentStatus);
        Assert.notNull(submittableClass);

        Query query = query(where("submissionId").is(submissionId).and("status").is(currentStatus.name()));
        Update update = update("status",newStatus.name());

        WriteResult writeResult = mongoTemplate.updateMulti(query,update,submittableClass);

        logger.info("Set status for {} in submission {}, changing statuses from {} to {}, changed {}",
                submittableClass,
                submissionId,
                currentStatus, newStatus,
                writeResult.getN());

    }

}
