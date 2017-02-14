package uk.ac.ebi.subs.repository.repos;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.Submission;
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
    public Page<SubmissionStatus> findAll(Pageable pageable);

    // Prevents POST /things and PATCH /things/:id
    @Override
    @RestResource(exported = true)
    public <S extends SubmissionStatus> S save(S s);

    // exported as DELETE /things/:id
    @Override
    @RestResource(exported = false)
    public void delete(SubmissionStatus t);

    @RestResource(exported = false)
    SubmissionStatus findBySubmissionId(String submissionId);

    @RestResource(exported = false)
    default SubmissionStatus findBySubmission(Submission submission) {
        return this.findBySubmissionId(submission.getId());
    }

    @RestResource(exported = false)
    void deleteBySubmissionId(String submissionId);

}
