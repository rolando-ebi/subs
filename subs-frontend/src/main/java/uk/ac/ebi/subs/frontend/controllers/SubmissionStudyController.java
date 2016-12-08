package uk.ac.ebi.subs.frontend.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.model.SubmissionStudy;
import uk.ac.ebi.subs.repository.repo.SubmissionStudyRepo;

import static uk.ac.ebi.subs.frontend.helpers.SubsPostHelper.deleteRemovedResponse;
import static uk.ac.ebi.subs.frontend.helpers.SubsPostHelper.postCreatedResponse;
import static uk.ac.ebi.subs.frontend.helpers.SubsPostHelper.putUpdatedResponse;

@RestController
@BasePathAwareController
@RequestMapping("/domains/{domainName}/submissions/{submissionId}/studies")
public class SubmissionStudyController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    SubmissionStudyRepo submissionStudyRepo;

    @Autowired
    ResourceAssembler<SubmissionStudy, Resource<Study>> submissionStudyResourceAssembler;

    @Autowired
    PagedResourcesAssembler pagedResourcesAssembler;

    @RequestMapping(path="/{itemId}",method = RequestMethod.GET)
    public Resource<Study> getOne(@PathVariable String domainName,@PathVariable String submissionId,@PathVariable String itemId){
        SubmissionStudy study = submissionStudyRepo.findOneByDomainSubmissionIdAndItemId(domainName,submissionId,itemId);

        if (study == null){
            throw new ResourceNotFoundException();
        }

        return submissionStudyResourceAssembler.toResource(study);
    }

    @RequestMapping(method = RequestMethod.GET)
    public PagedResources<Resource<Study>> listSome(@PathVariable String domainName,@PathVariable String submissionId, Pageable pageable){

        Page<SubmissionStudy> submissionStudies = submissionStudyRepo.findByDomainNameAndSubmissionId(domainName,submissionId,pageable);

        return pagedResourcesAssembler.toResource(submissionStudies,submissionStudyResourceAssembler);

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> postOne(
            @PathVariable String domainName,
            @PathVariable String submissionId,
            @Validated @RequestBody Study study){
        SubmissionStudy submissionStudy = new SubmissionStudy();
        submissionStudy.populate(domainName,submissionId,study);

        submissionStudyRepo.insert(submissionStudy);

        Resource<Study> studyResource = submissionStudyResourceAssembler.toResource(submissionStudy);

        return postCreatedResponse(studyResource);
    }



    @RequestMapping(path="/{itemId}",method=RequestMethod.PUT)
    public ResponseEntity<Void> putOne(
            @PathVariable String domainName,
            @PathVariable String submissionId,
            @PathVariable String itemId,
            @Validated @RequestBody Study study){
        SubmissionStudy storedSubmissionStudy = submissionStudyRepo.findOneByDomainSubmissionIdAndItemId(domainName,submissionId,itemId);

        if (storedSubmissionStudy == null){
            throw new ResourceNotFoundException();
        }

        SubmissionStudy submissionStudy = new SubmissionStudy();
        submissionStudy.populate(domainName,submissionId,itemId,study);

        submissionStudyRepo.save(submissionStudy);

        return putUpdatedResponse();
    }

    @RequestMapping(path="/{itemId}",method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteOne(
            @PathVariable String domainName,
            @PathVariable String submissionId,
            @PathVariable String itemId,
            @Validated @RequestBody Study study){
        SubmissionStudy storedSubmissionStudy = submissionStudyRepo.findOneByDomainSubmissionIdAndItemId(domainName,submissionId,itemId);

        if (storedSubmissionStudy == null){
            throw new ResourceNotFoundException();
        }

        SubmissionStudy submissionStudy = new SubmissionStudy();
        submissionStudy.populate(domainName,submissionId,itemId,study);

        submissionStudyRepo.delete(submissionStudy);

        return deleteRemovedResponse();
    }


}
