package uk.ac.ebi.subs.frontend.services;


import uk.ac.ebi.subs.data.Submission;

/**
 * Send a submission off to be processed
 */
public interface SubmissionProcessingService {

    void submitSubmissionForProcessing(Submission submission);

    void deleteSubmissionContents(Submission submission);
}
