package uk.ac.ebi.subs.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.frontend.controllers.SubmissionController;
import uk.ac.ebi.subs.frontend.controllers.SubmissionStudyController;
import uk.ac.ebi.subs.repository.model.SubmissionStudy;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Configuration
public class ResourceAssemblerConfig {

    @Bean
    ResourceAssembler<Submission, Resource<Submission>> submissionResourceAssembler() {
        return submission -> {
            Resource<Submission> submissionResource = new Resource<>(submission);

            submissionResource.add(
                    ControllerLinkBuilder.linkTo(
                            methodOn(SubmissionController.class)
                                    .getOne(submission.getDomain().getName(), submission.getId()
                                    )
                    ).withSelfRel()
            );

            //TODO add other relations

            return submissionResource;
        };
    }


    @Bean
    ResourceAssembler<Study, Resource<Study>> studyResourceAssembler() {
        return study -> {
            Resource<Study> studyResource = new Resource<>(study);


            //TODO add links

            return studyResource;
        };
    }

    @Bean
    ResourceAssembler<SubmissionStudy, Resource<Study>> submissionStudyResourceAssembler(){
        return submissionStudy -> {
            Resource<Study> studyResource = new Resource<>(submissionStudy.getDocument());

            //TODO add links

            studyResource.add(
                    ControllerLinkBuilder.linkTo(
                            methodOn(SubmissionStudyController.class)
                                    .getOne(submissionStudy.getDomainName(), submissionStudy.getSubmissionId(), submissionStudy.getAlias()
                                    )
                    ).withSelfRel()
            );

            return studyResource;
        };
    }
}
