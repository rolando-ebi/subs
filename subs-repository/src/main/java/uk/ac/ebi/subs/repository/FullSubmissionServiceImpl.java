package uk.ac.ebi.subs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uk.ac.ebi.subs.data.FullSubmission;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.EgaDataset;
import uk.ac.ebi.subs.repository.submittable.*;

import java.util.stream.Stream;

@Service
public class FullSubmissionServiceImpl implements FullSubmissionService {

    @Autowired  SubmissionRepository submissionRepository;

    @Autowired  AnalysisRepository analysisRepository;
    @Autowired  AssayDataRepository assayDataRepository;
    @Autowired  AssayRepository assayRepository;
    @Autowired  EgaDacPolicyRepository egaDacPolicyRepository;
    @Autowired  EgaDacRepository egaDacRepository;
    @Autowired  EgaDatasetRepository egaDatasetRepository;
    @Autowired  ProjectRepository projectRepository;
    @Autowired  ProtocolRepository protocolRepository;
    @Autowired  SampleGroupRepository sampleGroupRepository;
    @Autowired  SampleRepository sampleRepository;
    @Autowired  StudyRepository studyRepository;

    @Override
    public FullSubmission fetchOne(String submissionId) {
        Submission minimalSub = submissionRepository.findOne(submissionId);

        if (minimalSub == null){
            throw new ResourceNotFoundException();
        }

        FullSubmission submission = new FullSubmission(minimalSub);

        submission.setAnalyses(analysisRepository.findBySubmissionId(submissionId));
        submission.setAssayData(assayDataRepository.findBySubmissionId(submissionId));
        submission.setAssays(assayRepository.findBySubmissionId(submissionId));
        submission.setEgaDacPolicies(egaDacPolicyRepository.findBySubmissionId(submissionId));
        submission.setEgaDacs(egaDacRepository.findBySubmissionId(submissionId));
        submission.setEgaDatasets(egaDatasetRepository.findBySubmissionId(submissionId));
        submission.setProjects(projectRepository.findBySubmissionId(submissionId));
        submission.setProtocols(protocolRepository.findBySubmissionId(submissionId));
        submission.setSampleGroups(sampleGroupRepository.findBySubmissionId(submissionId));
        submission.setSamples(sampleRepository.findBySubmissionId(submissionId));
        submission.setStudies(studyRepository.findBySubmissionId(submissionId));

        return submission;
    }

    @Override
    public void storeFullSubmission(FullSubmission fullSubmission) {
        Submission minimalSub = new Submission(fullSubmission);

        submissionRepository.insert(minimalSub);


        fullSubmission.setId(minimalSub.getId());
        fullSubmission.allSubmissionItemsStream().forEach(i -> i.setSubmissionId());

        analysisRepository.insert(fullSubmission.getAnalyses());
        assayDataRepository.insert(fullSubmission.getAssayData());
        assayRepository.insert(fullSubmission.getAssays());
        egaDacPolicyRepository.insert(fullSubmission.getEgaDacPolicies());
        egaDacRepository.insert(fullSubmission.getEgaDacs());
        egaDatasetRepository.insert(fullSubmission.getEgaDatasets());
        projectRepository.insert(fullSubmission.getProjects());
        protocolRepository.insert(fullSubmission.getProtocols());
        sampleGroupRepository.insert(fullSubmission.getSampleGroups());
        sampleRepository.insert(fullSubmission.getSamples());
        studyRepository.insert(fullSubmission.getStudies());
    }
}
