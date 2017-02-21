package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.repository.model.StoredSubmittable;

import java.util.List;

@NoRepositoryBean
@RepositoryRestResource
public interface SubmittableRepository<T extends StoredSubmittable> extends MongoRepository<T, String>, SubmittableRepositoryCustom<T> {

    // exported as GET /things/:id
    @Override
    @RestResource(exported = false)
    public T findOne(String id);

    // exported as GET /things
    @Override
    @RestResource(exported = false)
    public Page<T> findAll(Pageable pageable);

    // Prevents POST /things and PATCH /things/:id
    @Override
    @RestResource(exported = true)
    public <S extends T> S save(S s);

    // exported as DELETE /things/:id
    @Override
    @RestResource(exported = true)
    public void delete(T t);


    @RestResource(exported = false)
    List<T> findBySubmissionId(String submissionId);

    @RestResource(exported = true,path="by-submission", rel="by-submission")
    Page<T> findBySubmissionId(@Param(value = "submissionId") String submissionId, Pageable pageable);

    @RestResource(exported = true,path="history")
    Page<T> findByDomainNameAndAliasOrderByCreatedDateDesc(
            @Param(value="domainName") String domainName, @Param(value="alias")String alias,
            Pageable pageable);

    @RestResource(exported = false)
    void deleteBySubmissionId(String submissionId);

}
