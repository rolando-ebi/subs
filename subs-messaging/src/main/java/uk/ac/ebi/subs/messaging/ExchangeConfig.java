package uk.ac.ebi.subs.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Exchange config - setup a topic exchange for submissions
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

}
