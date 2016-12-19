package uk.ac.ebi.subs.frontend.validators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;


@Configuration
public class ValidatorConfig {


    @Autowired
    private StudyValidator studyValidator;

    @Autowired
    private SubmissionValidator submissionValidator;

    @Autowired
    private SampleValidator sampleValidator;

    @Autowired
    private ProjectValidator projectValidator;

    @Bean
    public Validator beforeCreateStudyValidator() {
        return studyValidator;
    }

    @Bean
    public Validator beforeSaveStudyValidator() {
        return studyValidator;
    }

    @Bean
    public Validator beforeCreateSubmissionValidator() {
        return submissionValidator;
    }

    @Bean
    public Validator beforeSaveSubmissionValidator() {
        return submissionValidator;
    }

    @Bean
    public Validator beforeCreateSampleValidator() {
        return sampleValidator;
    }

    @Bean
    public Validator beforeSaveSampleValidator() {
        return sampleValidator;
    }

    @Bean
    public Validator beforeCreateProjectValidator() {
        return projectValidator;
    }

    @Bean
    public Validator beforeSaveProjectValidator() {
        return projectValidator;
    }

}
