package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.repository.model.SubmissionStatus;


public interface SubmissionStatusRepository extends MongoRepository<SubmissionStatus,String> {

    SubmissionStatus findBySubmissionId(String submissionId);

    void deleteBySubmissionId(String submissionId);

}
