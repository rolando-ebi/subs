package uk.ac.ebi.subs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import uk.ac.ebi.subs.data.Submission;

@SpringBootApplication
public class FrontendApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication( FrontendApplication.class);
        ApplicationPidFileWriter applicationPidFileWriter = new ApplicationPidFileWriter();
        springApplication.addListeners( applicationPidFileWriter );
        springApplication.run(args);
    }
}