package uk.ac.ebi.subs.enaagent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Service;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.processing.SubmissionEnvelope;
import uk.ac.ebi.subs.data.component.Archive;
import uk.ac.ebi.subs.data.component.SampleRef;
import uk.ac.ebi.subs.data.component.SampleUse;
import uk.ac.ebi.subs.data.submittable.*;
import uk.ac.ebi.subs.enarepo.EnaAssayDataRepository;
import uk.ac.ebi.subs.enarepo.EnaAssayRepository;
import uk.ac.ebi.subs.enarepo.EnaStudyRepository;
import uk.ac.ebi.subs.messaging.Exchanges;
import uk.ac.ebi.subs.messaging.Queues;
import uk.ac.ebi.subs.messaging.Topics;
import uk.ac.ebi.subs.processing.AgentResults;
import uk.ac.ebi.subs.processing.Certificate;
import uk.ac.ebi.subs.processing.ProcessingStatus;

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
    public void handleSubmission(SubmissionEnvelope submissionEnvelope) {
        logger.info("received submission {}, most recent handler was ",
                submissionEnvelope.getSubmission().getId());

        AgentResults agentResults = processSubmission(submissionEnvelope);

        logger.info("processed submission {}",submissionEnvelope.getSubmission().getId());

        rabbitMessagingTemplate.convertAndSend(Exchanges.SUBMISSIONS,Topics.EVENT_SUBMISSION_AGENT_RESULTS, agentResults);

        logger.info("sent submission {}",submissionEnvelope.getSubmission().getId());
    }

    public AgentResults processSubmission(SubmissionEnvelope submissionEnvelope) {

        List<Certificate> certs = new ArrayList<>();

        submissionEnvelope.getSubmission().getStudies().stream()
                .filter(s -> s.getArchive() == Archive.Ena)
                .forEach(s -> certs.add(processStudy(s, submissionEnvelope)));


        submissionEnvelope.getSubmission().getAssays().stream()
                .filter(a -> a.getArchive() == Archive.Ena)
                .forEach(a -> certs.add(processAssay(a, submissionEnvelope)));



        submissionEnvelope.getSubmission().getAssayData().stream()
                .filter(ad -> ad.getArchive() == Archive.Ena)
                .forEach(ad -> certs.add(processAssayData(ad, submissionEnvelope)));

        return new AgentResults(submissionEnvelope.getSubmission().getId(),certs);
    }


    private Certificate processStudy(Study study, SubmissionEnvelope submissionEnvelope) {

        if (!study.isAccessioned()) {
            study.setAccession("ENA-STU-" + UUID.randomUUID());
        }

        enaStudyRepository.save(study);
        study.setStatus(processedStatusValue);

        return new Certificate(study,Archive.Ena, ProcessingStatus.Processed, study.getAccession());
    }


    private Certificate processAssay(Assay assay, SubmissionEnvelope submissionEnvelope) {
        Submission submission = submissionEnvelope.getSubmission();

        for (SampleUse su : assay.getSampleUses()){
            SampleRef sr = su.getSampleRef();
            sr.fillIn(submission.getSamples(),submissionEnvelope.getSupportingSamples());
        }




        assay.getStudyRef().fillIn(submission.getStudies());



        if (!assay.isAccessioned()) {
            assay.setAccession("ENA-EXP-" + UUID.randomUUID());
        }

        enaAssayRepository.save(assay);
        assay.setStatus(processedStatusValue);

        return new Certificate(assay,Archive.Ena, ProcessingStatus.Processed, assay.getAccession());
    }

    private Certificate processAssayData(AssayData assayData, SubmissionEnvelope submissionEnvelope) {
        assayData.getAssayRef().fillIn(submissionEnvelope.getSubmission().getAssays());

        if (!assayData.isAccessioned()) {
            assayData.setAccession("ENA-RUN-" + UUID.randomUUID());
        }

        enaAssayDataRepository.save(assayData);

        assayData.setStatus(processedStatusValue);

        return new Certificate(assayData,Archive.Ena, ProcessingStatus.Processed, assayData.getAccession());
    }
}