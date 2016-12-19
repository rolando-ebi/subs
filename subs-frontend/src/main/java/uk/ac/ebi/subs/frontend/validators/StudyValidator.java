package uk.ac.ebi.subs.frontend.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.repository.SubmissionRepository;

@Component
public class StudyValidator extends AbstractSubmittableValidator {

    @Autowired
    public StudyValidator(SubmissionRepository submissionRepository) {
        super(submissionRepository);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Study.class.equals(clazz);
    }

}
