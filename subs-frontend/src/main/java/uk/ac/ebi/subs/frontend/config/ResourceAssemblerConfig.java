package uk.ac.ebi.subs.frontend.config;

import org.springframework.context.annotation.Configuration;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Configuration
public class ResourceAssemblerConfig {
/*
    @Bean
    ResourceAssembler<Submission, Resource<Submission>> submissionResourceAssembler() {
        return submission -> {
            Resource<Submission> submissionResource = new Resource<>(submission);

            //self rel
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
*/

}
