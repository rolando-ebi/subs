package uk.ac.ebi.subs.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.subs.frontend.handlers.SubmissionEventHandler;
import uk.ac.ebi.subs.frontend.handlers.SubmissionStudyEventHandler;

@Configuration
public class RepositoryHandlerConfig {

    @Bean
    SubmissionEventHandler submissionEventHandler() {
        return new SubmissionEventHandler();
    }

    @Bean
    SubmissionStudyEventHandler submissionStudyEventHandler() {
        return new SubmissionStudyEventHandler();}

}
