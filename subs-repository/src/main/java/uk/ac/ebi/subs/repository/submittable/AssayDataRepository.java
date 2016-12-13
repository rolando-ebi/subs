package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.SubmissionLinks;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.AssayData;

import java.util.List;

@RepositoryRestResource( path="/assayData",collectionResourceRel = "assayData")//the plural of assay data is assay data#
public interface AssayDataRepository extends MongoRepository<AssayData, String> {

    @RestResource(rel= SubmissionLinks.ASSAY_DATA)
    Page<AssayData> findBySubmissionId(@Param("submissionId") String submissionId, Pageable pageable);


    @RestResource(exported = false)
    List<AssayData> findBySubmissionId(String submissionId);
}
