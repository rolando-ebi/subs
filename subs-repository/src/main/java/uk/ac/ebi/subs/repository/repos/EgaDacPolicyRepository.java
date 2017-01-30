package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.SubmissionLinks;
import uk.ac.ebi.subs.data.submittable.EgaDacPolicy;

public interface EgaDacPolicyRepository extends SubmittableRepository<EgaDacPolicy, String> {

    @RestResource(rel= SubmissionLinks.EGA_DAC_POLICY)
    Page<EgaDacPolicy> findBySubmissionId(@Param("submissionId") String submissionId, Pageable pageable);

}
