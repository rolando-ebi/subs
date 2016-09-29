package uk.ac.ebi.subs.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.subs.data.submittable.Submission;
import uk.ac.ebi.subs.messaging.Topics;
import uk.ac.ebi.subs.messaging.Exchanges;
import uk.ac.ebi.subs.repository.SubmissionService;

@RestController
public class SubmissionController {

    private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);

    @Autowired
    SubmissionService submissionService;


    RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    public SubmissionController(RabbitMessagingTemplate rabbitMessagingTemplate, MessageConverter messageConverter) {
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
        this.rabbitMessagingTemplate.setMessageConverter(messageConverter);
    }

    @RequestMapping(value = "/api/submit", method = RequestMethod.PUT)
    public void submit(@RequestBody Submission submission) {
        logger.info("received submission for domain {}",submission.getDomain().getName());

        submission.allSubmissionItems().forEach(
                s -> {
                    if (s.getDomain() == null) {
                        s.setDomain(submission.getDomain());
                    }
                }
        );

        submissionService.storeSubmission(submission);
        logger.info("stored submission {}",submission.getId());

        //TODO debug stuff
        submission.setLastHandler(this.getClass().toString());


        rabbitMessagingTemplate.convertAndSend(Exchanges.SUBMISSIONS, Topics.EVENT_SUBMISSION_SUBMITTED, submission);

        logger.info("sent submission {}",submission.getId());
    }
}