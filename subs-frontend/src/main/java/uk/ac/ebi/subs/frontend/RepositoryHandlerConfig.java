package uk.ac.ebi.subs.frontend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.subs.frontend.handlers.SubmissionEventHandler;

@Configuration
public class RepositoryHandlerConfig {

    @Bean
    SubmissionEventHandler submissionEventHandler() {
        return new SubmissionEventHandler();
    }

}
