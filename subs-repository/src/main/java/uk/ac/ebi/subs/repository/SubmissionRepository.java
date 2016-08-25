package uk.ac.ebi.subs.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.Submission;

public interface SubmissionRepository extends MongoRepository<Submission,String>{
}
