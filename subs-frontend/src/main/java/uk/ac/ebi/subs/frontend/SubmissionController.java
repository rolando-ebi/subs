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
import uk.ac.ebi.subs.processing.SubmissionEnvelope;
import uk.ac.ebi.subs.data.validation.SubmissionValidator;
import uk.ac.ebi.subs.messaging.Exchanges;
import uk.ac.ebi.subs.messaging.Topics;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.submittable.*;

import java.util.UUID;

@RestController
public class SubmissionController {

    private static final Logger logger = LoggerFactory.getLogger(SubmissionController.class);

    @Autowired
    SubmissionValidator submissionValidator;

    @Autowired SubmissionRepository submissionRepository;
    @Autowired AnalysisRepository analysisRepository;
    @Autowired AssayRepository assayRepository;
    @Autowired AssayDataRepository assayDataRepository;
    @Autowired EgaDacRepository egaDacRepository;
    @Autowired EgaDacPolicyRepository egaDacPolicyRepository;
    @Autowired EgaDatasetRepository egaDatasetRepository;
    @Autowired ProjectRepository projectRepository;
    @Autowired ProtocolRepository protocolRepository;
    @Autowired SampleRepository sampleRepository;
    @Autowired SampleGroupRepository sampleGroupRepository;
    @Autowired StudyRepository studyRepository;

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

        submission.setId(UUID.randomUUID().toString());

        submission.allSubmissionItemsStream().forEach(
                s -> {
                    if (s.getDomain() == null) {
                        s.setDomain(submission.getDomain());
                    }
                    s.setId(UUID.randomUUID().toString());
                }
        );

        saveSubmissionContents(submission);

        SubmissionEnvelope submissionEnvelope = new SubmissionEnvelope(submission);

        rabbitMessagingTemplate.convertAndSend(
                Exchanges.SUBMISSIONS,
                Topics.EVENT_SUBMISSION_SUBMITTED,
                submissionEnvelope
        );


        logger.info("sent submission {}", submission.getId());
    }

    private void saveSubmissionContents(Submission submission) {
        analysisRepository.save(submission.getAnalyses());
        logger.debug("saved analyses {}");

        assayRepository.save(submission.getAssays());
        logger.debug("saved assays {}");

        assayDataRepository.save(submission.getAssayData());
        logger.debug("saved assayData {}");

        egaDacRepository.save(submission.getEgaDacs());
        logger.debug("saved egaDacs {}");

        egaDacPolicyRepository.save(submission.getEgaDacPolicies());
        logger.debug("saved egaDacPolicies {}");

        egaDatasetRepository.save(submission.getEgaDatasets());
        logger.debug("saved egaDatasets {}");

        projectRepository.save(submission.getProjects());
        logger.debug("saved projects {}");

        protocolRepository.save(submission.getProtocols());
        logger.debug("saved protocols {}");

        sampleRepository.save(submission.getSamples());
        logger.debug("saved samples {}");

        sampleGroupRepository.save(submission.getSampleGroups());
        logger.debug("saved sampleGroups {}");

        studyRepository.save(submission.getStudies());
        logger.debug("saved studies {}");

        submissionRepository.save(submission);
        logger.info("saved submission {}", submission.getId());
    }
}