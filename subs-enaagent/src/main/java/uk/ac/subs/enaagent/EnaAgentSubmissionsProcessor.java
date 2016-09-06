package uk.ac.subs.enaagent;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Service;
import uk.ac.ebi.subs.data.component.Realm;
import uk.ac.ebi.subs.data.submittable.*;
import uk.ac.ebi.subs.messaging.Channels;
import uk.ac.subs.enarepo.EnaAssayDataRepository;
import uk.ac.subs.enarepo.EnaAssayRepository;
import uk.ac.subs.enarepo.EnaStudyRepository;

import java.util.*;


@Service
public class EnaAgentSubmissionsProcessor {

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


    @RabbitListener(queues = {Channels.ENA_PROCESSING})
    public void handleSubmission(Submission submission) {

        processSubmission(submission);

        rabbitMessagingTemplate.convertAndSend(Channels.SUBMISSION_PROCESSED, submission);
    }

    public void processSubmission(Submission submission) {

        submission.getStudies().stream()
                .filter(s -> s.getRealm() == Realm.Sequencing)
                .forEach(s -> processStudy(s, submission));


        submission.getAssays().stream()
                .filter(a -> a.getRealm() == Realm.Sequencing)
                .forEach(a -> processAssay(a, submission));



        submission.getAssayData().stream()
                .filter(ad -> ad.getRealm() == Realm.Sequencing)
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