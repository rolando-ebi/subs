package uk.ac.ebi.subs.repository.repos.submittables;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.repository.model.Sample;
import uk.ac.ebi.subs.repository.projections.SubmittableWithStatus;

@RepositoryRestResource(excerptProjection = SubmittableWithStatus.class)
public interface SampleRepository extends SubmittableRepository<Sample> {

    @RestResource(exported = false)
    Sample findByAccession(String accession);
}
