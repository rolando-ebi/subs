package uk.ac.ebi.subs.dispatcher;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.stereotype.Service;
import uk.ac.ebi.subs.data.submittable.Submission;
import uk.ac.ebi.subs.data.submittable.Submittable;
import uk.ac.ebi.subs.messaging.Channels;


@Service
public class DispatchProcessor {

    RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    public DispatchProcessor(RabbitMessagingTemplate rabbitMessagingTemplate, MessageConverter messageConverter){
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
        this.rabbitMessagingTemplate.setMessageConverter(messageConverter);
    }


    @RabbitListener(queues={Channels.SUBMISSION_SUBMITTED,Channels.SUBMISSION_PROCESSED})
    public void handleSubmissionEvent(Submission submission){


        /*
        * this is a deliberately simple implementation for prototyping
        * we will need to redo this as we flesh out the system
        * */

        int sampleCount = 0;
        int enaCount = 0;
        int arrayExpressCount = 0;

        for (Submittable submittable : submission.allSubmissionItems()){
            if (submittable.getAccession() != null && !submittable.getAccession().isEmpty()){
                continue;
            }
            switch (submittable.getRealm()) {
                case Usi: sampleCount++; break;
                case Arrays: arrayExpressCount++; break;
                case Sequencing: enaCount++; break;
                case FunctionalGenomics: arrayExpressCount++; break;
            }
        }

        String targetQueue = null;

        if (sampleCount > 0){
            targetQueue = Channels.SAMPLES_PROCESSING;
        }
        else if (enaCount > 0){
            targetQueue = Channels.ENA_PROCESSING;
        }
        else if (arrayExpressCount > 0){
            targetQueue = Channels.AE_PROCESSING;
        }

        if (targetQueue != null){
            rabbitMessagingTemplate.convertAndSend(targetQueue,submission);
        }



    }
}