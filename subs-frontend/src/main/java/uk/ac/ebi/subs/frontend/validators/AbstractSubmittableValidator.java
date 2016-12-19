package uk.ac.ebi.subs.frontend.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Submittable;
import uk.ac.ebi.subs.repository.SubmissionRepository;


public abstract class AbstractSubmittableValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final
    SubmissionRepository submissionRepository;

    @Autowired
    public AbstractSubmittableValidator(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.warn("validate {}",target);
        Submittable submittable = (Submittable)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"submissionId","field.required");

        if (submittable.getSubmissionId() != null){
            Submission submission = submissionRepository.findOne(submittable.getSubmissionId());

            if (submission == null){
                errors.rejectValue("submissionId","field.submissionNotFound");
            }
        }
    }
}
