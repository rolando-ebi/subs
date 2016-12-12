package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.Protocol;

import java.util.List;

public interface ProtocolRepository extends MongoRepository<Protocol, String>{

    @RestResource(exported = false)
    List<Protocol> findBySubmissionId(String submissionId);
}
