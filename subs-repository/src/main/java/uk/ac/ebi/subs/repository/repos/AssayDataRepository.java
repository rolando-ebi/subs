package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uk.ac.ebi.subs.repository.model.AssayData;

@RepositoryRestResource(path = "/assayData", collectionResourceRel = "assayData")
//the plural of assay data is assay data#
public interface AssayDataRepository extends SubmittableRepository<AssayData> {


}
