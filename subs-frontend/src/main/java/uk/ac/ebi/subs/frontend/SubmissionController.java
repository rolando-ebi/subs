package uk.ac.ebi.subs.frontend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.status.ProcessingStatus;
import uk.ac.ebi.subs.processing.SubmissionEnvelope;
import uk.ac.ebi.subs.data.validation.SubmissionValidator;
import uk.ac.ebi.subs.messaging.Exchanges;
import uk.ac.ebi.subs.messaging.Topics;
import uk.ac.ebi.subs.repository.SubmissionRepository;

import java.util.UUID;

@RestController
public class SubmissionController {

    private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);

    @Autowired
    SubmissionValidator submissionValidator;

    @Autowired
    SubmissionRepository submissionRepository;

    RabbitMessagingTemplate rabbitMessagingTemplate;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(submissionValidator);
    }

    @Autowired
    public SubmissionController(RabbitMessagingTemplate rabbitMessagingTemplate, MessageConverter messageConverter) {
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
        this.rabbitMessagingTemplate.setMessageConverter(messageConverter);
    }

    @RequestMapping(value = "/api/submit", method = RequestMethod.POST)
    public Submission submit(@Validated @RequestBody Submission submission) {
        logger.info("received submission for domain {}", submission.getDomain().getName());

        submission.setStatus(ProcessingStatus.Submitted.name());

        submission.setId(UUID.randomUUID().toString());


        submission.allSubmissionItemsStream().forEach(
                s -> {
                    if (s.getDomain() == null) {
                        s.setDomain(submission.getDomain());
                    }
                    s.setId(UUID.randomUUID().toString());
                }
        );

        submissionRepository.save(submission);
        logger.info("saved submission {}", submission.getId());

        SubmissionEnvelope submissionEnvelope = new SubmissionEnvelope(submission);

        rabbitMessagingTemplate.convertAndSend(
                Exchanges.SUBMISSIONS,
                Topics.EVENT_SUBMISSION_SUBMITTED,
                submissionEnvelope
        );


        logger.info("sent submission {}", submission.getId());

        return submission;
    }

}