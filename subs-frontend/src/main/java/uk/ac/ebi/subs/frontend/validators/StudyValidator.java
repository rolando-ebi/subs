package uk.ac.ebi.subs.frontend.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.status.Status;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.data.submittable.Submittable;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.submittable.StudyRepository;

import java.util.Collection;

@Component
public class StudyValidator extends AbstractSubmittableValidator {

    private StudyRepository studyRepository;

    @Override
    Submittable getCurrentVersion(String id) {
        return studyRepository.findOne(id);
    }

    @Autowired
    public StudyValidator(
            StudyRepository repository,
            SubmissionRepository submissionRepository,
            Collection<Status> processingStatuses,
            Collection<Status> releaseStatuses) {
        super(submissionRepository, processingStatuses, releaseStatuses);
        this.studyRepository = repository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Study.class.equals(clazz);
    }

}
