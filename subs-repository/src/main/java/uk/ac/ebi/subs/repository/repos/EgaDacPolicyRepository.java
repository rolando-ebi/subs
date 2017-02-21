package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.ac.ebi.subs.repository.model.EgaDacPolicy;
import uk.ac.ebi.subs.repository.projections.SubmittableWithStatus;

@RepositoryRestResource(excerptProjection = SubmittableWithStatus.class)
public interface EgaDacPolicyRepository extends SubmittableRepository<EgaDacPolicy> {


}
