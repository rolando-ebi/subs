package uk.ac.ebi.subs.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

@Configuration
public class ExchangeConfig {



    @Bean
    TopicExchange submissionExchange() {
        return new TopicExchange(Exchanges.SUBMISSIONS);
    }


    @Bean Queue suppInfoQueue() {return new Queue(Queues.SUBMISSION_SUPPORTING_INFO,true); }

    @Bean Queue dispatcherQueue() {
        return new Queue(Queues.SUBMISSION_DISPATCHER,true);
    }

    @Bean Queue monitorQueue() {return new Queue(Queues.SUBMISSION_MONITOR,true); }

    @Bean Queue biosamplesQueue() {
        return new Queue(Queues.BIOSAMPLES_AGENT,true);
    }

    @Bean Queue enaQueue() {
        return new Queue(Queues.ENA_AGENT,true);
    }

    @Bean Queue aeQueue() {
        return new Queue(Queues.AE_AGENT,true);
    }

    @Bean Queue sampleSuppInfoQueue() {return new Queue(Queues.SUBMISSION_NEEDS_SAMPLE_INFO,true); }

    @Bean Queue suppInfoProvidedQueue() {return new Queue(Queues.SUBMISSION_SUPPORTING_INFO_PROVIDED,true); }


    @Bean
    Binding dispatcherBinding(Queue dispatcherQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(dispatcherQueue).to(submissionExchange).with(Queues.SUBMISSION_DISPATCHER_ROUTING_KEY);
    }

    @Bean
    Binding suppInfoBinding(Queue suppInfoQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(suppInfoQueue).to(submissionExchange).with(Queues.SUBMISSION_SUPPORTING_INFO_ROUTING_KEY);
    }

    @Bean
    Binding suppInfoProvidedBinding(Queue suppInfoProvidedQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(suppInfoProvidedQueue).to(submissionExchange).with(Queues.SUBMISSION_SUPPORTING_INFO_PROVIDED_ROUTING_KEY);
    }

    @Bean Binding monitorBinding(Queue monitorQueue, TopicExchange submissionExchange){
        return BindingBuilder.bind(monitorQueue).to(submissionExchange).with(Queues.SUBMISSION_MONITOR_ROUTING_KEY);
    }

    @Bean Binding sampleSuppInfoBinding(Queue sampleSuppInfoQueue, TopicExchange submissionExchange){
        return BindingBuilder.bind(sampleSuppInfoQueue).to(submissionExchange).with(Queues.SUBMISSION_NEEDS_SAMPLE_INFO_ROUTING_KEY);
    }

    @Bean
    Binding biosamplesBinding(Queue biosamplesQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(biosamplesQueue).to(submissionExchange).with(Topics.SAMPLES_PROCESSING);
    }


    @Bean
    Binding enaBinding(Queue enaQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(enaQueue).to(submissionExchange).with(Topics.ENA_PROCESSING);
    }

    @Bean
    Binding aeBinding(Queue aeQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(aeQueue).to(submissionExchange).with(Topics.AE_PROCESSING);
    }


}
