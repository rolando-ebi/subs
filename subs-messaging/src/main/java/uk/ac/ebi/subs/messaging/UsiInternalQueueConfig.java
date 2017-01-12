package uk.ac.ebi.subs.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsiInternalQueueConfig {

    /**
     * Queue for submissions to be checked for dispatch to archive agents
     * @return
     */
    @Bean
    Queue dispatcherQueue() {
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
     * Queue for submission envelopes that have had supporting information added
     * @return
     */
    @Bean Queue suppInfoProvidedQueue() {return new Queue(Queues.SUBMISSION_SUPPORTING_INFO_PROVIDED,true); }

    @Bean
    Binding suppInfoProvidedBinding(Queue suppInfoProvidedQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(suppInfoProvidedQueue).to(submissionExchange).with(Queues.SUBMISSION_SUPPORTING_INFO_PROVIDED_ROUTING_KEY);
    }

    /**
     * Queue for submissions to be preppred for dispatch
     * @return
     */
    @Bean Queue onSubmitForwardToDispatchQueue() {return new Queue(Queues.SUBMISSION_SUBMITTED_DO_DISPATCH,true); }

    @Bean
    Binding onSubmitForwardToDispatchBinding(Queue onSubmitForwardToDispatchQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(onSubmitForwardToDispatchQueue).to(submissionExchange).with(Queues.SUBMISSION_SUBMITTED_ROUTING_KEY);
    }

    /**
     * Queue for submissions to be checked to see if they need supporting info
     * @return
     */
    @Bean Queue onSubmitCheckForSupportingInfoQueue() {return new Queue(Queues.SUBMISSION_SUBMITTED_CHECK_SUPPORTING_INFO,true); }


    @Bean
    Binding suppInfoBinding(Queue onSubmitCheckForSupportingInfoQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(onSubmitCheckForSupportingInfoQueue).to(submissionExchange).with(Queues.SUBMISSION_SUBMITTED_ROUTING_KEY);
    }


}
