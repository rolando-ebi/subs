package uk.ac.ebi.subs.frontend.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.status.Status;
import uk.ac.ebi.subs.data.status.SubmissionStatus;
import uk.ac.ebi.subs.data.submittable.Submittable;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.model.StoredSubmittable;
import uk.ac.ebi.subs.repository.repos.SubmittableRepository;

import java.util.List;

/**
 * Base validator for submitted items
 * <p>
 * Ensures that we have a submission ID and that it relates to a real submission
 * <p>
 * Note that we must supply a default message. Not having a message causes the client to get a 500 (server error)
 * status code instead of a 400 (bad request)
 */
@Component
public class CoreSubmittableValidationHelper {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    final SubmissionRepository submissionRepository;
    final List<Status> processingStatuses;
    final List<Status> releaseStatuses;

    @Autowired
    public CoreSubmittableValidationHelper(
            SubmissionRepository submissionRepository,
            List<Status> processingStatuses,
            List<Status> releaseStatuses) {
        this.submissionRepository = submissionRepository;
        this.processingStatuses = processingStatuses;
        this.releaseStatuses = releaseStatuses;
    }

    public void validate(StoredSubmittable target, SubmittableRepository repository, Errors errors) {
        StoredSubmittable storedVersion = null;

        if (target.getId() != null) {
            storedVersion = (StoredSubmittable) repository.findOne(target.getId());
        }

        this.validate(target, storedVersion, errors);
    }


    /*TODO review error codes, I just made some up for now */
    public void validate(StoredSubmittable target, StoredSubmittable storedVersion, Errors errors) {
        logger.info("validate {}", target);
        StoredSubmittable submittable = (StoredSubmittable) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "submission", "required", "submission is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "required", "status is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "domain.name", "required", "domain name is required");



        if (submittable.getSubmission() == null) {
            Submission submission = submittable.getSubmission();

            if (!submission.getStatus().equals(SubmissionStatus.Draft.name())) {
                errors.reject("submissionLocked","Submission has been submitted, changes are not possible");
            }

        }

        //submittables have their IDs set on creation, so having an ID does not mean it is already stored
        if (submittable.getId() != null && storedVersion != null) {
            validateAgainstStoredVersion(errors, submittable, storedVersion);
        }
    }

    private void validateAgainstStoredVersion(Errors errors, StoredSubmittable submittable, StoredSubmittable storedVersion) {

        ValidationHelper.thingCannotChange(
                submittable.getSubmission(),
                storedVersion.getSubmission(),
                "submission",
                errors
        );

        ValidationHelper.thingCannotChange(
                submittable.getDomain(),
                storedVersion.getDomain(),
                "domain",
                errors
        );


        ValidationHelper.validateStatusChange(
                submittable.getStatus(),
                storedVersion.getStatus(),
                processingStatuses,
                "status",
                errors);

    }
}
