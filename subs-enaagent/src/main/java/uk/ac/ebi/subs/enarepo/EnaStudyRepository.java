package uk.ac.ebi.subs.enarepo;


import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.Study;

public interface EnaStudyRepository extends MongoRepository<Study,String>{
}
