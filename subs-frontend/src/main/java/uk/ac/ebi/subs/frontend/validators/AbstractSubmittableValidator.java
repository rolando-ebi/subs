package uk.ac.ebi.subs.frontend.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.status.Status;
import uk.ac.ebi.subs.data.submittable.Submittable;
import uk.ac.ebi.subs.repository.SubmissionRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Base validator for submitted items
 * <p>
 * Ensures that we have a submission ID and that it relates to a real submission
 * <p>
 * Note that we must supply a default message. Not having a message causes the client to get a 500 (server error)
 * status code instead of a 400 (bad request)
 */
public abstract class AbstractSubmittableValidator implements Validator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    abstract Submittable getCurrentVersion(String id);

    final SubmissionRepository submissionRepository;
    final Collection<Status> processingStatuses;
    final Collection<Status> releaseStatuses;

    @Autowired
    public AbstractSubmittableValidator(
            SubmissionRepository submissionRepository,
            Collection<Status> processingStatuses,
            Collection<Status> releaseStatuses) {
        this.submissionRepository = submissionRepository;
        this.processingStatuses = processingStatuses;
        this.releaseStatuses = releaseStatuses;
    }


    /*TODO review error codes, I just made some up for now */
    @Override
    public void validate(Object target, Errors errors) {
        logger.warn("validate {}", target);
        Submittable submittable = (Submittable) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "submissionId", "field.required", "submissionId is required");

        if (submittable.getSubmissionId() != null) {
            Submission submission = submissionRepository.findOne(submittable.getSubmissionId());

            if (submission == null) {
                errors.rejectValue("submissionId", "field.submissionNotFound", "submission not found for ID");
            }
        }

        if (submittable.getId() != null) {
            Submittable storedVersion = getCurrentVersion(submittable.getId());

            if (storedVersion == null) {
                errors.rejectValue("id", "field.idUnknown", "ID does not match any record");
            } else {
                validateAgainstStoredVersion(errors, submittable, storedVersion);

            }

        }
    }

    private void validateAgainstStoredVersion(Errors errors, Submittable submittable, Submittable storedVersion) {
        if (storedVersion.getSubmissionId() != submittable.getSubmissionId()) {
            errors.rejectValue("submissionId", "field.submissionChanged", "Submission ID cannot be changed");
        }

        if (!storedVersion.getDomain().getName().equals(submittable.getDomain().getName())) {
            errors.rejectValue("domain.name", "field.domainName.changed", "Domain name cannot be changed");
        }

        if (!storedVersion.getStatus().equals(submittable.getStatus())) {
            validateProcessingStatusTransition(errors, submittable, storedVersion);
        }

    }

    private void validateProcessingStatusTransition(Errors errors, Submittable submittable, Submittable storedVersion) {

        Optional<Status> optionalCurrentStatus = processingStatuses.stream().filter(s -> s.getStatusName().equals(storedVersion.getStatus())).findFirst();

        if (!optionalCurrentStatus.isPresent()) {
            throw new IllegalStateException(
                    "Cannot validate status transition, stored status " + storedVersion.getStatus()
                            + "is not in the processing status list " + processingStatuses);
        }

        Status currentStatus = optionalCurrentStatus.get();

        if (!currentStatus.isUserTransitionPermitted(submittable.getStatus())){
            errors.rejectValue("status","field.illegalStateTransition","This status change is not permitted");
        }
    }
}
