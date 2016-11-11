package uk.ac.ebi.subs.repository.processing;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;


public interface SupportingSampleRepository extends MongoRepository<SupportingSample, String> {

    @Query(value="{ 'submissionId' : ?0 }")
    List<SupportingSample> findBySubmissionId(@Param(value="submissionId") String submissionsId);
}
