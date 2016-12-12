package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.Assay;

import java.util.List;

public interface AssayRepository extends MongoRepository<Assay, String> {

    @RestResource(exported = false)
    List<Assay> findBySubmissionId(String submissionId);
}
