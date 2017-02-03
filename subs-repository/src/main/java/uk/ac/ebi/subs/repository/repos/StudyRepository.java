package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.SubmissionLinks;
import uk.ac.ebi.subs.repository.model.Study;

@RepositoryRestResource
public interface StudyRepository extends SubmittableRepository<Study> {

}
