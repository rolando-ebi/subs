package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.EgaDacPolicy;

import java.util.List;

public interface EgaDacPolicyRepository extends MongoRepository<EgaDacPolicy, String> {

    @RestResource(exported = false)
    List<EgaDacPolicy> findBySubmissionId(String submissionId);
}
