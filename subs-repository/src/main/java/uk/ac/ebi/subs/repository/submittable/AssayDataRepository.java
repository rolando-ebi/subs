package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.AssayData;

import java.util.List;

public interface AssayDataRepository extends MongoRepository<AssayData, String> {

    @RestResource(exported = false)
    List<AssayData> findBySubmissionId(String submissionId);
}
