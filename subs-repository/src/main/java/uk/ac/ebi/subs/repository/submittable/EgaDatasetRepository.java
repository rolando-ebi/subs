package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.EgaDataset;

import java.util.List;

public interface EgaDatasetRepository extends MongoRepository<EgaDataset, String> {

    @RestResource(exported = false)
    List<EgaDataset> findBySubmissionId(String submissionId);
}
