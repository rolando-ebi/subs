package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Submittable;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface SubmittableRepository<T extends Submittable,ID extends Serializable> extends MongoRepository<T, ID> {

    @RestResource(exported = false)
    List<T> findBySubmissionId(ID submissionId);
}
