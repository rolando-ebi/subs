package uk.ac.ebi.subs.api.validators;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import uk.ac.ebi.subs.api.services.OperationControlService;
import uk.ac.ebi.subs.repository.repos.SubmissionRepository;
import uk.ac.ebi.subs.repository.model.Submission;

@Component
public class SubmissionValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SubmissionValidator(
            SubmissionRepository submissionRepository,
            TeamValidator teamValidator,
            SubmitterValidator submitterValidator,
            OperationControlService operationControlService
    ) {
        this.submissionRepository = submissionRepository;
        this.teamValidator = teamValidator;
        this.submitterValidator = submitterValidator;
        this.operationControlService = operationControlService;
    }


    private SubmissionRepository submissionRepository;
    private TeamValidator teamValidator;
    private SubmitterValidator submitterValidator;
    private OperationControlService operationControlService;


    @Override
    public void validate(Object target, Errors errors) {

        Submission submission = (Submission) target;

        SubsApiErrors.rejectIfEmptyOrWhitespace(errors,"submitter");
        SubsApiErrors.rejectIfEmptyOrWhitespace(errors,"team");

        if (errors.hasErrors()) return;

        try {
            errors.pushNestedPath("team");
            ValidationUtils.invokeValidator(this.teamValidator, submission.getTeam(), errors);
        } finally {
            errors.popNestedPath();
        }

        try {
            errors.pushNestedPath("submitter");
            ValidationUtils.invokeValidator(this.submitterValidator, submission.getSubmitter(), errors);
        } finally {
            errors.popNestedPath();
        }

        if (submission.getId() != null) {
            Submission storedVersion = submissionRepository.findOne(submission.getId());

            if (storedVersion != null) {


                if (!operationControlService.isUpdateable(submission)) {
                    SubsApiErrors.resource_locked.addError(errors);
                } else {
                    validateAgainstStoredVersion(submission, storedVersion, errors);
                }
            }
        }

        if (errors.hasErrors()) {
            logger.error("validation has errors {}", errors.getAllErrors());
        } else {
            logger.error("no validation errors");
        }

    }

    private void validateAgainstStoredVersion(Submission target, Submission storedVersion, Errors errors) {

        submitterCannotChange(target, storedVersion, errors);

        teamCannotChange(target, storedVersion, errors);

        createdDateCannotChange(target, storedVersion, errors);

        submittedDateCannotChange(target, storedVersion, errors);
    }

    private void submitterCannotChange(Submission target, Submission storedVersion, Errors errors) {
        ValidationHelper.thingCannotChange(
                target.getSubmitter(),
                storedVersion.getSubmitter(),
                "submitter",
                errors
        );
    }

    private void teamCannotChange(Submission target, Submission storedVersion, Errors errors) {
        ValidationHelper.thingCannotChange(
                target.getTeam(),
                storedVersion.getTeam(),
                "team",
                errors
        );
    }

    private void createdDateCannotChange(Submission target, Submission storedVersion, Errors errors) {
        /*Yes, this is stupid
         * Spring Data Auditing is set for this object, but it doesn't maintain the createdDate on save
         */

        target.setCreatedDate(storedVersion.getCreatedDate());
    }

    private void submittedDateCannotChange(Submission target, Submission storedVersion, Errors errors) {
        ValidationHelper.thingCannotChange(
                target.getSubmissionDate(),
                storedVersion.getSubmissionDate(),
                "submissionDate",
                errors
        );
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Submission.class.isAssignableFrom(clazz);
    }
}
