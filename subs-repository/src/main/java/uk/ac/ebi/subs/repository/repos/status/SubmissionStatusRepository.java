package uk.ac.ebi.subs.repository.repos.status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.repository.model.SubmissionStatus;


@RepositoryRestResource
public interface SubmissionStatusRepository extends MongoRepository<SubmissionStatus, String> {

    // exported as GET /things/:id
    @Override
    @RestResource(exported = true)
    public SubmissionStatus findOne(String id);

    // exported as GET /things
    @Override
    @RestResource(exported = false)
    Page<SubmissionStatus> findAll(Pageable pageable);

    // Prevents POST /things and PATCH /things/:id
    @Override
    @RestResource(exported = true)
    <S extends SubmissionStatus> S save(S s);

    // exported as DELETE /things/:id
    @Override
    @RestResource(exported = false)
    void delete(SubmissionStatus t);


}
