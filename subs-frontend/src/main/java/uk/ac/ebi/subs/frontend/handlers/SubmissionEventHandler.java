package uk.ac.ebi.subs.frontend.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.frontend.services.SubmissionProcessingService;
import uk.ac.ebi.subs.frontend.exceptions.ResourceLockedException;
import uk.ac.ebi.subs.processing.ProcessingStatus;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.frontend.updateability.OperationControlService;

import java.util.UUID;

/**
 * Repo event handler for submissions in the frontend
 *
 *  * locks down changes to non-draft submissions, based on the OperationControlService
 *  * send submissions off to rabbit after storing a submission with the 'Submitted' status
 */
@RepositoryEventHandler(Submission.class)
public class SubmissionEventHandler {

    @Autowired
    private OperationControlService operationControlService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SubmissionProcessingService submissionProcessingService;

    /**
     * make sure the submission is ready for storing
     *  * give it an ID if it has not got one
     *  * check it can be modified if there it already exists
     * @param submission
     */
    @HandleBeforeCreate @HandleBeforeSave public void handleBeforeSave(Submission submission) {
        if (submission.getId() == null){
            //new submission
            submission.setId(UUID.randomUUID().toString());
        }
        else {
            Submission storedSubmission = submissionRepository.findOne(submission.getId());

            if (storedSubmission != null){
                if (!operationControlService.isUpdateable(storedSubmission)){
                    throw new ResourceLockedException();
                }
            }
        }
    }


    /**
     * Once the submission has been stored, if it has a status of submitted, submit it for processing
     * @param submission
     */
    @HandleAfterCreate @HandleAfterSave public void handleAfterSave(Submission submission){
        if (submission.getStatus() != null && submission.getStatus().equals(ProcessingStatus.Submitted.name())){
            submissionProcessingService.submitSubmissionForProcessing(submission);
        }
    }



}
