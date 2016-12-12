package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;

import java.util.List;

public interface AnalysisRepository extends MongoRepository<Analysis, String> {

    @RestResource(exported = false)
    List<Analysis> findBySubmissionId(String submissionId);
}
