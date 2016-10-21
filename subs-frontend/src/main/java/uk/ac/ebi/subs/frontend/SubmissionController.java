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
import uk.ac.ebi.subs.data.SubmissionEnvelope;
import uk.ac.ebi.subs.data.validation.SubmissionValidator;
import uk.ac.ebi.subs.messaging.Exchanges;
import uk.ac.ebi.subs.messaging.Topics;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.submittable.AssayDataRepository;
import uk.ac.ebi.subs.repository.submittable.AssayRepository;
import uk.ac.ebi.subs.repository.submittable.SampleRepository;

import java.util.UUID;

@RestController
public class SubmissionController {

    private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);

    @Autowired
    SubmissionValidator submissionValidator;

    @Autowired SubmissionRepository submissionRepository;
    @Autowired SampleRepository sampleRepository;
    @Autowired AssayRepository assayRepository;
    @Autowired AssayDataRepository assayDataRepository;

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

    @RequestMapping(value = "/api/submit", method = RequestMethod.PUT)
    public void submit(@Validated @RequestBody Submission submission) {
        logger.info("received submission for domain {}", submission.getDomain().getName());

        submission.allSubmissionItems().forEach(
                s -> {
                    if (s.getDomain() == null) {
                        s.setDomain(submission.getDomain());
                    }
                    s.setId(UUID.randomUUID().toString());
                }
        );

        saveSubmissionContents(submission);

        SubmissionEnvelope submissionEnvelope = new SubmissionEnvelope(submission);
        submissionEnvelope.addHandler(this.getClass());

        rabbitMessagingTemplate.convertAndSend(
                Exchanges.SUBMISSIONS,
                Topics.EVENT_SUBMISSION_SUBMITTED,
                submissionEnvelope
        );

        logger.info("sent submission {}", submission.getId());
    }

    private void saveSubmissionContents(Submission submission) {

        sampleRepository.save(submission.getSamples());
        logger.debug("saved samples {}");

        assayRepository.save(submission.getAssays());
        logger.debug("saved assays {}");

        assayDataRepository.save(submission.getAssayData());
        logger.debug("saved assayData {}");

        submissionRepository.save(submission);
        logger.info("saved submission {}", submission.getId());
    }
}