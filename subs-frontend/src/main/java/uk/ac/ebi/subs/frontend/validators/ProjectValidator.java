package uk.ac.ebi.subs.frontend.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import uk.ac.ebi.subs.data.submittable.Project;
import uk.ac.ebi.subs.repository.SubmissionRepository;

@Component
public class ProjectValidator extends AbstractSubmittableValidator{

    @Autowired
    public ProjectValidator(SubmissionRepository submissionRepository) {
        super(submissionRepository);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Project.class.isAssignableFrom(clazz);
    }


}
