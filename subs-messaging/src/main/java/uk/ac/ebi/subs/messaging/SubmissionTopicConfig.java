package uk.ac.ebi.subs.messaging;


import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubmissionTopicConfig {
    private final String topic = Topics.SUBMISSIONS;

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topic);
    }

}
