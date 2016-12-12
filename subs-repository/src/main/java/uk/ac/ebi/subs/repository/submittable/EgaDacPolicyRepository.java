package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.SubmissionLinks;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.EgaDacPolicy;

import java.util.List;

public interface EgaDacPolicyRepository extends MongoRepository<EgaDacPolicy, String> {

    @RestResource(rel= SubmissionLinks.EGA_DAC_POLICY)
    Page<EgaDacPolicy> findBySubmissionId(@Param("submissionId") String submissionId, Pageable pageable);


    @RestResource(exported = false)
    List<EgaDacPolicy> findBySubmissionId(String submissionId);
}
