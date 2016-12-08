package uk.ac.ebi.subs.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Study;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Configuration
public class ResourceAssemblerConfiguration {

    @Bean
    ResourceAssembler<Submission, Resource<Submission>> submissionResourceAssembler() {
        return submission -> {
            Resource<Submission> submissionResource = new Resource<>(submission);

            submissionResource.add(
                    linkTo(
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
}
