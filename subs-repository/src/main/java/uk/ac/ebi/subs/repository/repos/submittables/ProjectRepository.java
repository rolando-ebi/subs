package uk.ac.ebi.subs.repository.repos.submittables;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.ac.ebi.subs.repository.model.Project;
import uk.ac.ebi.subs.repository.projections.SubmittableWithStatus;

@RepositoryRestResource(excerptProjection = SubmittableWithStatus.class)
public interface ProjectRepository extends SubmittableRepository<Project> {

}
