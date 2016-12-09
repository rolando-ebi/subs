package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.PathVariable;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Study;

@RepositoryRestResource
public interface StudyRepository extends MongoRepository<Study, String> {

}
