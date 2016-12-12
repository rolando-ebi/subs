package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.SubmissionLinks;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.Protocol;

import java.util.List;

public interface ProtocolRepository extends MongoRepository<Protocol, String>{
    @RestResource(rel= SubmissionLinks.PROTOCOL)
    Page<Protocol> findBySubmissionId(@Param("submissionId") String submissionId, Pageable pageable);

    @RestResource(exported = false)
    List<Protocol> findBySubmissionId(String submissionId);
}
