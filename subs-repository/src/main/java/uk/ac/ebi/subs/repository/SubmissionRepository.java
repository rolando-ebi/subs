package uk.ac.ebi.subs.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.repository.model.Submission;

@RepositoryRestResource
public interface SubmissionRepository extends MongoRepository<Submission, String> {

    // exported as GET /things/:id
    @Override
    @RestResource(exported = true)
    public Submission findOne(String id);

    // exported as GET /things
    @Override
    @RestResource(exported = false)
    public Page<Submission> findAll(Pageable pageable);

    // Prevents POST /things and PATCH /things/:id
    @Override
    @RestResource(exported = true)
    public <S extends Submission> S save(S s);

    // exported as DELETE /things/:id
    @Override
    @RestResource(exported = true)
    public void delete(Submission t);

    @Query(value = "{ 'domain.name' : ?0 }")
    @RestResource(exported = false)
    Page<Submission> findByDomainName(@Param(value = "domainName") String domainName, Pageable pageable);


}
