package uk.ac.ebi.subs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.Submission;

@RepositoryRestResource
public interface SubmissionRepository extends MongoRepository<Submission,String>{

    @Query(value="{ 'domain.name' : ?0 }")
    @RestResource(rel="byDomain")
    Page<Submission> findByDomainName(@Param(value="domainName") String domainName, Pageable pageable);


}
