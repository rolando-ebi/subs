package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.Sample;

import java.util.List;

public interface SampleRepository extends MongoRepository<Sample, String> {

    Sample findByAccession(String accession);

    @RestResource(exported = false)
    List<Sample> findBySubmissionId(String submissionId);

}
