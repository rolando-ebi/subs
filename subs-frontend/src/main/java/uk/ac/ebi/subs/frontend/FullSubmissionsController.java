package uk.ac.ebi.subs.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.subs.data.FullSubmission;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.frontend.helpers.SubsResponseHelper;
import uk.ac.ebi.subs.repository.FullSubmissionService;

@RestController
@BasePathAwareController
public class FullSubmissionsController {

    @Autowired
    FullSubmissionService fullSubmissionService;

    @Autowired
    SubsResponseHelper subsResponseHelper;

    @RequestMapping(path="/fullSubmissions", method= RequestMethod.POST)
    public ResponseEntity<Void> postFullSubmission(@RequestBody FullSubmission fullSubmission){

        fullSubmissionService.storeFullSubmission(fullSubmission);

        Submission submission = new Submission(fullSubmission);

        return subsResponseHelper.postCreatedResponse(submission);
    }
}
