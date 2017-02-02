package uk.ac.ebi.subs.repository;


import uk.ac.ebi.subs.data.FullSubmission;

public interface FullSubmissionService {

    FullSubmission fetchOne(String submissionId);
    void storeFullSubmission(FullSubmission fullSubmission);
}
