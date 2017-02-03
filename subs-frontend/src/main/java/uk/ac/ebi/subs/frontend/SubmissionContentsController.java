package uk.ac.ebi.subs.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.subs.frontend.resourceAssembly.ProcessorBackedAssembler;
import uk.ac.ebi.subs.repository.model.*;
import uk.ac.ebi.subs.repository.repos.*;

@RestController
@BasePathAwareController
@RequestMapping(value = "/submission/{submissionId}", method = RequestMethod.GET)
public class SubmissionContentsController {

    @RequestMapping("/analyses")
    public PagedResources<Resource<Analysis>> submissionAnalyses(
            @PathVariable String submissionId,
            Pageable pageable) {
        Page<Analysis> page = analysisRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<Analysis>(analysisResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/assays")
    public PagedResources<Resource<Assay>> submissionAssays(
            @PathVariable String submissionId,
            Pageable pageable) {
        Page<Assay> page = assayRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(assayResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/assayData")
    public PagedResources<Resource<AssayData>> submissionAssayData(
            @PathVariable String submissionId,
            Pageable pageable) {
        Page<AssayData> page = assayDataRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(assayDataResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/egaDacs")
    public PagedResources<Resource<EgaDac>> submissionEgaDacs(
            @PathVariable String submissionId,
            Pageable pageable) {
        Page<EgaDac> page = egaDacRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(egaDacResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/egaDacPolicies")
    public PagedResources<Resource<EgaDacPolicy>> submissionEgaDacPolicies(
            @PathVariable String submissionId,
            Pageable pageable) {
        Page<EgaDacPolicy> page = egaDacPolicyRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(egaDacPolicyResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/egaDatasets")
    public PagedResources<Resource<EgaDataset>> submissionEgaDatasets(
            @PathVariable String submissionId,
            Pageable pageable) {
        Page<EgaDataset> page = egaDatasetRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(egaDatasetResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/project")
    public PagedResources<Resource<Project>> submissionProjects(
            @PathVariable String submissionId,
            Pageable pageable) {
        Page<Project> page = projectRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(projectResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/protocols")
    public PagedResources<Resource<Protocol>> submissionProtocols(
            @PathVariable String submissionId,
            Pageable pageable) {
        Page<Protocol> page = protocolRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(protocolResourceProcessor,entityLinks)
        );
    }


    @RequestMapping("/samples")
    public PagedResources<Resource<Sample>> submissionSamples(
            @PathVariable String submissionId,
            Pageable pageable
    ) {
        Page<Sample> page = sampleRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(sampleResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/sampleGroups")
    public PagedResources<Resource<SampleGroup>> submissionSampleGroups(
            @PathVariable String submissionId,
            Pageable pageable
    ) {
        Page<SampleGroup> page = sampleGroupRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(sampleGroupResourceProcessor,entityLinks)
        );
    }

    @RequestMapping("/studies")
    public PagedResources<Resource<Study>> submissionStudies(
            @PathVariable String submissionId,
            Pageable pageable
    ) {
        Page<Study> page = studyRepository.findBySubmissionId(submissionId, pageable);

        return pagedResourcesAssembler.toResource(
                page,
                new ProcessorBackedAssembler<>(studyResourceProcessor,entityLinks)
        );
    }


    @Autowired
    AnalysisRepository analysisRepository;
    @Autowired
    AssayRepository assayRepository;
    @Autowired
    AssayDataRepository assayDataRepository;
    @Autowired
    EgaDacRepository egaDacRepository;
    @Autowired
    EgaDacPolicyRepository egaDacPolicyRepository;
    @Autowired
    EgaDatasetRepository egaDatasetRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProtocolRepository protocolRepository;
    @Autowired
    SampleRepository sampleRepository;
    @Autowired
    SampleGroupRepository sampleGroupRepository;
    @Autowired
    StudyRepository studyRepository;

    @Autowired
    PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    EntityLinks entityLinks;

    @Autowired
    ResourceProcessor<Resource<Analysis>> analysisResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<Assay>> assayResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<AssayData>> assayDataResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<EgaDac>> egaDacResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<EgaDacPolicy>> egaDacPolicyResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<EgaDataset>> egaDatasetResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<Project>> projectResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<Protocol>> protocolResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<Sample>> sampleResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<SampleGroup>> sampleGroupResourceProcessor;
    @Autowired
    ResourceProcessor<Resource<Study>> studyResourceProcessor;

}
