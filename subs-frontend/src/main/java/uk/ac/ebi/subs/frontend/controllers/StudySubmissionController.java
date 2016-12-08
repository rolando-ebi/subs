package uk.ac.ebi.subs.frontend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.repository.SubmissionRepository;

import java.util.UUID;

@RestController
@BasePathAwareController
@RequestMapping("/submissions/{submissionId}/studies")
public class StudySubmissionController {

    private static final Logger logger = LoggerFactory.getLogger(StudySubmissionController.class);

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ResourceAssembler<Study,Resource<Study>> studyResourceAssembler;

    @RequestMapping(method = RequestMethod.POST)
    public Resource<Study> postStudyToSubmission(@PathVariable String submissionId, @RequestBody Study study){
        Submission submission = submissionRepository.findOne(submissionId);

        if (submission == null){
            throw new ResourceNotFoundException("No submission for that ID");
        }

        study.setId(UUID.randomUUID().toString());

        submission.getStudies().add(study);

        submissionRepository.save(submission);

        return studyResourceAssembler.toResource(study);
    }



}
