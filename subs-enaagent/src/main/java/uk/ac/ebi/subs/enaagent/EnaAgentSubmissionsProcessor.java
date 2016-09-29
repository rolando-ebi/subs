package uk.ac.ebi.subs.enaagent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Service;
import uk.ac.ebi.subs.data.component.Archive;
import uk.ac.ebi.subs.data.submittable.*;
import uk.ac.ebi.subs.enarepo.EnaAssayDataRepository;
import uk.ac.ebi.subs.enarepo.EnaAssayRepository;
import uk.ac.ebi.subs.enarepo.EnaStudyRepository;
import uk.ac.ebi.subs.messaging.Exchanges;
import uk.ac.ebi.subs.messaging.Queues;
import uk.ac.ebi.subs.messaging.Topics;

import java.util.*;


@Service
public class EnaAgentSubmissionsProcessor {

    private static final Logger logger = LoggerFactory.getLogger(EnaAgentSubmissionsProcessor.class);

    String processedStatusValue = "processed";

    @Autowired
    EnaStudyRepository enaStudyRepository;

    @Autowired
    EnaAssayRepository enaAssayRepository;

    @Autowired
    EnaAssayDataRepository enaAssayDataRepository;

    RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    public EnaAgentSubmissionsProcessor(RabbitMessagingTemplate rabbitMessagingTemplate, MessageConverter messageConverter) {
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
        this.rabbitMessagingTemplate.setMessageConverter(messageConverter);
    }


    @RabbitListener(queues = {Queues.ENA_AGENT})
    public void handleSubmission(Submission submission) {
        logger.info("received submission {}",submission.getId());

        processSubmission(submission);

        logger.info("processed submission {}",submission.getId());

        rabbitMessagingTemplate.convertAndSend(Exchanges.SUBMISSIONS,Topics.EVENT_SUBMISSION_PROCESSED, submission);

        logger.info("sent submission {}",submission.getId());
    }

    public void processSubmission(Submission submission) {

        submission.getStudies().stream()
                .filter(s -> s.getArchive() == Archive.Ena)
                .forEach(s -> processStudy(s, submission));


        submission.getAssays().stream()
                .filter(a -> a.getArchive() == Archive.Ena)
                .forEach(a -> processAssay(a, submission));



        submission.getAssayData().stream()
                .filter(ad -> ad.getArchive() == Archive.Ena)
                .forEach(ad -> processAssayData(ad, submission));
    }


    private void processStudy(Study study, Submission submission) {

        if (!study.isAccessioned()) {
            study.setAccession("ENA-STU-" + UUID.randomUUID());
        }

        enaStudyRepository.save(study);
        study.setStatus(processedStatusValue);
    }


    private void processAssay(Assay assay, Submission submission) {

        assay.getSampleRef().fillIn(submission.getSamples());
        assay.getStudyRef().fillIn(submission.getStudies());

        if (!assay.isAccessioned()) {
            assay.setAccession("ENA-EXP-" + UUID.randomUUID());
        }

        enaAssayRepository.save(assay);
        assay.setStatus(processedStatusValue);
    }

    private void processAssayData(AssayData assayData, Submission submission) {
        assayData.getAssayRef().fillIn(submission.getAssays());

        if (!assayData.isAccessioned()) {
            assayData.setAccession("ENA-RUN-" + UUID.randomUUID());
        }

        enaAssayDataRepository.save(assayData);

        assayData.setStatus(processedStatusValue);
    }
}