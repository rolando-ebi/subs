package uk.ac.ebi.subs.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.subs.data.submittable.Submission;
import uk.ac.ebi.subs.repository.SubmissionService;

@RestController
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @RequestMapping("/submissions")
    public Page<Submission> getSubmissions(Pageable pageable) {
        return submissionService.fetchSubmissions(pageable);
    }


    @RequestMapping(value = "/submit", method = RequestMethod.PUT)
    public void storeSubmission(@RequestBody Submission submission) {
        submissionService.storeSubmission(submission);
    }
}
