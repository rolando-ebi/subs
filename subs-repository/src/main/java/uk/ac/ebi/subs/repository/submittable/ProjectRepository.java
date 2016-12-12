package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.data.submittable.Project;

import java.util.List;

public interface ProjectRepository extends MongoRepository<Project, String> {
    @RestResource(exported = false)
    List<Project> findBySubmissionId(String submissionId);

}
