package uk.ac.ebi.subs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.data.submittable.Submission;
import uk.ac.ebi.subs.data.submittable.Submittable;

import java.util.List;

@RestResource
public interface SubmissionRepository extends MongoRepository<Submission,String>{

    @RestResource(path = "domainName")
    @Query(value="{ 'domain.name' : ?0 }")
    List<Submission> findByDomainName(String domainName);

    @RestResource(path = "domainName")
    @Query(value="{ 'domain.name' : ?0 }")
    Page<Submission> findByDomainName(String domainName,Pageable pageable);

}
