package uk.ac.ebi.subs.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubmissionChannelsConfig {

    private final String submittedQueueName = Channels.SUBMISSION_SUBMITTED;

    @Bean
    Queue submittedQueue() {
        return new Queue(submittedQueueName,true);
    }

    @Bean
    Binding submittedBinding(Queue submittedQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(submittedQueue).to(submissionExchange).with(submittedQueueName);
    }


    private final String processedQueueName = Channels.SUBMISSION_PROCESSED;

    @Bean
    Queue processedQueue() {
        return new Queue(processedQueueName,true);
    }

    @Bean
    Binding processedBinding(Queue processedQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(processedQueue).to(submissionExchange).with(processedQueueName);
    }

    private final String biosamplesQueueName = Channels.SAMPLES_PROCESSING;

    @Bean
    Queue biosamplesQueue() {
        return new Queue(biosamplesQueueName,true);
    }

    @Bean
    Binding biosamplesBinding(Queue biosamplesQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(biosamplesQueue).to(submissionExchange).with(biosamplesQueueName);
    }

    private final String enaQueueName = Channels.ENA_PROCESSING;

    @Bean
    Queue enaQueue() {
        return new Queue(enaQueueName,true);
    }

    @Bean
    Binding enaBinding(Queue enaQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(enaQueue).to(submissionExchange).with(enaQueueName);
    }

    private final String aeQueueName = Channels.AE_PROCESSING;

    @Bean
    Queue aeQueue() {
        return new Queue(aeQueueName,true);
    }

    @Bean
    Binding aeBinding(Queue aeQueue, TopicExchange submissionExchange) {
        return BindingBuilder.bind(aeQueue).to(submissionExchange).with(enaQueueName);
    }


}
