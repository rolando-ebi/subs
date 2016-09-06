package uk.ac.ebi.subs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.subs.data.submittable.Submission;

import java.util.List;

public interface SubmissionService {
    List<Submission> fetchSubmissions();

    Page<Submission> fetchSubmissions(Pageable pageable);

    Page<Submission> fetchSubmissionsByDomainName(Pageable pageable,String domainName);

    List<Submission> fetchSubmissionsByDomainName(String domainName);

    void storeSubmission(Submission submission);

    Submission fetchSubmission(String id);
}
