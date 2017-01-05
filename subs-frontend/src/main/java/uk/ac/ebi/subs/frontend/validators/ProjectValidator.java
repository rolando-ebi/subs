package uk.ac.ebi.subs.frontend.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.status.Status;
import uk.ac.ebi.subs.data.submittable.Project;
import uk.ac.ebi.subs.data.submittable.Submittable;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.submittable.ProjectRepository;

import java.util.Collection;

@Component
public class ProjectValidator extends AbstractSubmittableValidator {

    private ProjectRepository projectRepository;

    @Override
    Submittable getCurrentVersion(String id) {
        return projectRepository.findOne(id);
    }

    @Autowired
    public ProjectValidator(ProjectRepository projectRepository,
                            SubmissionRepository submissionRepository,
                            Collection<Status> processingStatuses,
                            Collection<Status> releaseStatuses) {
        super(submissionRepository, processingStatuses, releaseStatuses);
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Project.class.isAssignableFrom(clazz);
    }


}
