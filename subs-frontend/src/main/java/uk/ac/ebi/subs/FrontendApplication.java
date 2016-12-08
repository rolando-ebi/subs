package uk.ac.ebi.subs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;

@SpringBootApplication
public class FrontendApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(FrontendApplication.class, args);

        RepositoryRestConfiguration restConfiguration = ctx.getBean(RepositoryRestConfiguration.class);

        //TODO this property should be settable through the application.properties file in Spring BOOT 1.50

        restConfiguration.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
    }
}