package uk.ac.ebi.subs.frontend;


import uk.ac.ebi.subs.data.Submission;

/**
 * Send a submission off to be processed
 */
public interface SubmissionProcessingService {

    void submitSubmissionForProcessing(Submission submission);
}
