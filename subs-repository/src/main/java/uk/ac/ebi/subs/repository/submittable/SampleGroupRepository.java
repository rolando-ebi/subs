package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.SampleGroup;

import java.util.List;

public interface SampleGroupRepository extends MongoRepository<SampleGroup, String> {

    @RestResource(exported = false)
    List<SampleGroup> findBySubmissionId(String submissionId);
}
