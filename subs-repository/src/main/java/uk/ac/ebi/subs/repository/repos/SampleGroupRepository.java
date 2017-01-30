package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.SubmissionLinks;
import uk.ac.ebi.subs.repository.model.SampleGroup;

public interface SampleGroupRepository extends SubmittableRepository<SampleGroup, String> {

    @RestResource(rel= SubmissionLinks.SAMPLE_GROUP)
    Page<SampleGroup> findBySubmissionId(@Param("submissionId") String submissionId, Pageable pageable);

}
