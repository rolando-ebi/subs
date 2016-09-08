package uk.ac.ebi.subs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.data.submittable.Submission;
import uk.ac.ebi.subs.data.submittable.Submittable;

import java.util.List;

public interface SubmissionRepository extends MongoRepository<Submission,String>{

    @Query(value="{ 'domain.name' : ?0 }")
    List<Submission> findByDomainName(String domainName);

    @Query(value="{ 'domain.name' : ?0 }")
    Page<Submission> findByDomainName(String domainName,Pageable pageable);

}
