package uk.ac.ebi.subs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uk.ac.ebi.subs.data.FullSubmission;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.repository.repos.SubmissionRepository;
import uk.ac.ebi.subs.repository.repos.submittables.*;

@Service
public class FullSubmissionServiceImpl implements FullSubmissionService {

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    AnalysisRepository analysisRepository;
    @Autowired
    AssayDataRepository assayDataRepository;
    @Autowired
    AssayRepository assayRepository;
    @Autowired
    EgaDacPolicyRepository egaDacPolicyRepository;
    @Autowired
    EgaDacRepository egaDacRepository;
    @Autowired
    EgaDatasetRepository egaDatasetRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    ProtocolRepository protocolRepository;
    @Autowired
    SampleGroupRepository sampleGroupRepository;
    @Autowired
    SampleRepository sampleRepository;
    @Autowired
    StudyRepository studyRepository;

    @Override
    public FullSubmission fetchOne(String submissionId) {
        Submission minimalSub = submissionRepository.findOne(submissionId);

        if (minimalSub == null) {
            throw new ResourceNotFoundException();
        }

        FullSubmission submission = new FullSubmission(minimalSub);

        submission.getAnalyses().addAll(analysisRepository.findBySubmissionId(submissionId));
        submission.getAssayData().addAll(assayDataRepository.findBySubmissionId(submissionId));
        submission.getAssays().addAll(assayRepository.findBySubmissionId(submissionId));
        submission.getEgaDacPolicies().addAll(egaDacPolicyRepository.findBySubmissionId(submissionId));
        submission.getEgaDacs().addAll(egaDacRepository.findBySubmissionId(submissionId));
        submission.getEgaDatasets().addAll(egaDatasetRepository.findBySubmissionId(submissionId));
        submission.getProjects().addAll(projectRepository.findBySubmissionId(submissionId));
        submission.getProtocols().addAll(protocolRepository.findBySubmissionId(submissionId));
        submission.getSampleGroups().addAll(sampleGroupRepository.findBySubmissionId(submissionId));
        submission.getSamples().addAll(sampleRepository.findBySubmissionId(submissionId));
        submission.getStudies().addAll(studyRepository.findBySubmissionId(submissionId));

        return submission;
    }
}
