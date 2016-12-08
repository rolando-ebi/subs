package uk.ac.ebi.subs.repository.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.repository.model.SubmissionStudy;


public interface SubmissionStudyRepo extends MongoRepository<SubmissionStudy, String> {

    @Query(value="{ 'domainName' : ?0, 'submissionId' : ?1, '_id': ?2 }")
    SubmissionStudy findOneByDomainSubmissionIdAndItemId(
            @Param(value="domainName") String domainName,
            @Param(value="submissionId") String submissionId,
            @Param(value="_id") String id);

    @Query(value="{'domainName' : ?0, 'submissionId' : ?1}")
    Page<SubmissionStudy> findByDomainNameAndSubmissionId(
            @Param(value="domainName") String domainName,
            @Param(value="submissionId") String submissionId,
            Pageable pageable
    );
}
