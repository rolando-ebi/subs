package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.repository.model.StoredSubmittable;

import java.util.List;

@NoRepositoryBean
@RepositoryRestResource
public interface SubmittableRepository<T extends StoredSubmittable> extends MongoRepository<T, String>, SubmittableRepositoryCustom<T> {

    @RestResource(exported = false)
    List<T> findBySubmissionId(String submissionId);

    @RestResource(exported = false)
    Page<T> findBySubmissionId(String submissionId, Pageable pageable);

    @RestResource(exported = false)
    Page<T> findByDomainNameAndAliasOrderByCreatedDateDesc(String domainName, String alias, Pageable pageable);

}
