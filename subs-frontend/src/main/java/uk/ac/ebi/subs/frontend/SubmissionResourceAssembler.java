package uk.ac.ebi.subs.frontend;

import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.submittable.Submission;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubmissionResourceAssembler extends ResourceAssemblerSupport<Submission, Resource> {


    public SubmissionResourceAssembler() {
        super(SubmissionController.class, Resource.class);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Submission> submissions) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Submission submission : submissions) {
            resources.add(
                    new Resource<Submission>(
                            submission,
                            linkTo(methodOn(SubmissionController.class).getSubmission(submission.getId())).withSelfRel()
                    )
            );
        }
        return resources;
    }

    @Override
    public Resource toResource(Submission submission) {
        return new Resource<Submission>(
                submission,
                linkTo(methodOn(SubmissionController.class).getSubmission(submission.getId())).withSelfRel()
        );
    }

}
