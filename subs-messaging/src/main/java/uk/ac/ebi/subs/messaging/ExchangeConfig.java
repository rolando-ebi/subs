package uk.ac.ebi.subs.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

/**
 * This is the key class for binding topics to queues
 * This should be organised by queue, followed by the binding for that queue
 *
 *
 *
 *
 */
@Configuration
public class ExchangeConfig {

    /**
     * The Exchange for usi submissions
     * @return
     */
    @Bean
    TopicExchange submissionExchange() {
        return new TopicExchange(Exchanges.SUBMISSIONS);
    }

    /**
     * Queue for submissions to be checked to see if they need supporting info
     * @return
     */
    @Bean Queue suppInfoQueue() {return new Queue(Queues.SUBMISSION_SUPPORTING_INFO,true); }

    @Bean
    Binding suppInfoBinding(Queue suppInfoQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(suppInfoQueue).to(submissionExchange).with(Queues.SUBMISSION_SUPPORTING_INFO_ROUTING_KEY);
    }


    /**
     * Queue for submissions to be checked for dispatch to archive agents
     * @return
     */
    @Bean Queue dispatcherQueue() {
        return new Queue(Queues.SUBMISSION_DISPATCHER,true);
    }

    @Bean
    Binding dispatcherBinding(Queue dispatcherQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(dispatcherQueue).to(submissionExchange).with(Queues.SUBMISSION_DISPATCHER_ROUTING_KEY);
    }


    /**
     * Queue for certificate envelopes to update submission state
     * @return
     */
    @Bean Queue monitorQueue() {return new Queue(Queues.SUBMISSION_MONITOR,true); }

    @Bean Binding monitorBinding(Queue monitorQueue, TopicExchange submissionExchange){
        return BindingBuilder.bind(monitorQueue).to(submissionExchange).with(Queues.SUBMISSION_MONITOR_ROUTING_KEY);
    }

    /**
     * Queue for submission envelopes to be processed by biosamples
     * @return
     */
    @Bean Queue biosamplesQueue() {
        return new Queue(Queues.BIOSAMPLES_AGENT,true);
    }

    @Bean
    Binding biosamplesBinding(Queue biosamplesQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(biosamplesQueue).to(submissionExchange).with(Topics.SAMPLES_PROCESSING);
    }

    /**
     * Queue for certificates to update submission status
     * @return
     */
    @Bean Queue monitorSubStatusQueue() {return new Queue(Queues.SUBMISSION_MONITOR_STATUS_UPDATE);}

    @Bean Binding monitorStatusUpdateBinding(Queue monitorSubStatusQueue, TopicExchange submissionExchange){
        return BindingBuilder.bind(monitorSubStatusQueue).to(submissionExchange).with(Queues.SUBMISSION_MONITOR_STATUS_UPDATE_ROUTING_KEY);
    }


    /**
     * Queue for submission envelopes to be processed by ENA
     * @return
     */
    @Bean Queue enaQueue() {
        return new Queue(Queues.ENA_AGENT,true);
    }

    @Bean
    Binding enaBinding(Queue enaQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(enaQueue).to(submissionExchange).with(Topics.ENA_PROCESSING);
    }

    /**
     * Queue for submission envelopes to be processed by ArrayExpress
     * @return
     */
    @Bean Queue aeQueue() {
        return new Queue(Queues.AE_AGENT,true);
    }

    @Bean
    Binding aeBinding(Queue aeQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(aeQueue).to(submissionExchange).with(Topics.AE_PROCESSING);
    }

    /**
     * Queue for submission envelopes that need supporting information (samples)
     * @return
     */
    @Bean Queue sampleSuppInfoQueue() {return new Queue(Queues.SUBMISSION_NEEDS_SAMPLE_INFO,true); }

    @Bean Binding sampleSuppInfoBinding(Queue sampleSuppInfoQueue, TopicExchange submissionExchange){
        return BindingBuilder.bind(sampleSuppInfoQueue).to(submissionExchange).with(Queues.SUBMISSION_NEEDS_SAMPLE_INFO_ROUTING_KEY);
    }

    /**
     * Queue for submission envelopes that have had supporting information added
     * @return
     */
    @Bean Queue suppInfoProvidedQueue() {return new Queue(Queues.SUBMISSION_SUPPORTING_INFO_PROVIDED,true); }

    @Bean
    Binding suppInfoProvidedBinding(Queue suppInfoProvidedQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(suppInfoProvidedQueue).to(submissionExchange).with(Queues.SUBMISSION_SUPPORTING_INFO_PROVIDED_ROUTING_KEY);
    }

    /**
     * Queue for updated sample envelopes to be used by ENA
     * @return
     */
    @Bean Queue enaSamplesUpdated() {return new Queue(Queues.ENA_SAMPLES_UPDATED,true);}

    @Bean
    Binding enaSamplesUpdatedBinding(Queue enaSamplesUpdated, TopicExchange submissionExchange) {
        return BindingBuilder.bind(enaSamplesUpdated).to(submissionExchange).with(Queues.ENA_SAMPLES_UPDATED);
    }


    /**
     * Queue for updated sample envelopes to be used by AE
     * @return
     *
     */
    @Bean Queue aeSamplesUpdated() {return new Queue(Queues.AE_SAMPLES_UPDATED,true);}

    @Bean
    Binding aeSamplesUpdatedBinding(Queue aeSamplesUpdated, TopicExchange submissionExchange) {
        return BindingBuilder.bind(aeSamplesUpdated).to(submissionExchange).with(Queues.AE_SAMPLES_UPDATED);
    }






















}
