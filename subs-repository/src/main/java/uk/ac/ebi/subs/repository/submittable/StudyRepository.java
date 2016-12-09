package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.ac.ebi.subs.data.submittable.Study;

public interface StudyRepository extends MongoRepository<Study, String> {

}
