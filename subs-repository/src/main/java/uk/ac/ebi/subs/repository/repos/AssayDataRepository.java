package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.ac.ebi.subs.repository.model.AssayData;
import uk.ac.ebi.subs.repository.projections.SubmittableWithStatus;

@RepositoryRestResource(path = "/assayData", collectionResourceRel = "assayData", excerptProjection = SubmittableWithStatus.class)
//the plural of assay data is assay data#
public interface AssayDataRepository extends SubmittableRepository<AssayData> {


}
