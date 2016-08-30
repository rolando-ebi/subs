package uk.ac.ebi.subs.frontend;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.subs.data.submittable.Submission;
import uk.ac.ebi.subs.messaging.Channels;
import uk.ac.ebi.subs.repository.SubmissionService;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class SubmissionController {

    @Autowired
    SubmissionService submissionService;

    @Autowired
    SubmissionResourceAssembler submissionResourceAssembler;

    RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    public SubmissionController (RabbitMessagingTemplate rabbitMessagingTemplate, MessageConverter messageConverter){
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
        this.rabbitMessagingTemplate.setMessageConverter(messageConverter);
    }

    @RequestMapping(value="/submissions",method = RequestMethod.GET, produces = {"application/hal+json"} )
    public PagedResources<Submission> submissions(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<Submission> submissions= submissionService.fetchSubmissions(pageable);
        return assembler.toResource(submissions, submissionResourceAssembler);
    }




    @RequestMapping(value = "/submit", method = RequestMethod.PUT)
    public void submit(@RequestBody Submission submission){
        submissionService.storeSubmission(submission);
        rabbitMessagingTemplate.convertAndSend(Channels.SUBMISSION_SUBMITTED,submission);
    }

    @RequestMapping("/submission/{id}")
    public HttpEntity<SubmissionResource> getSubmission(@PathVariable("id") String id) {
        Submission s = submissionService.fetchSubmission(id);


        SubmissionResource sr = new SubmissionResource(s);
        sr.add(linkTo(methodOn(SubmissionController.class).getSubmission(id)).withSelfRel());

        return new ResponseEntity<SubmissionResource>(sr, HttpStatus.OK);
    }
}
