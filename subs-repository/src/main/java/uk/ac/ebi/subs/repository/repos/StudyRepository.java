package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.ac.ebi.subs.repository.model.Study;

@RepositoryRestResource
public interface StudyRepository extends SubmittableRepository<Study> {

}
