package uk.ac.ebi.subs.repository.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.repository.model.StoredSubmittable;


public interface SubmittableRepositoryCustom<T extends StoredSubmittable> {

    @RestResource(exported = true,path="by-domain")
    Page<T> submittablesInDomain(@Param(value="domainName") String domainName, Pageable pageable);
}
