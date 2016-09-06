package uk.ac.ebi.subs.enarepo;


import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.data.submittable.Submission;

public interface EnaStudyRepository extends MongoRepository<Study,String>{
}
