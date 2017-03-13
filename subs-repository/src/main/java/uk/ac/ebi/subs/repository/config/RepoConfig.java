package uk.ac.ebi.subs.repository.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.subs.repository.repos.submittables.*;

import java.util.Arrays;
import java.util.List;

@Configuration
public class RepoConfig {

    private AnalysisRepository analysisRepository;
    private AssayDataRepository assayDataRepository;
    private AssayRepository assayRepository;
    private EgaDacPolicyRepository egaDacPolicyRepository;
    private EgaDacRepository egaDacRepository;
    private EgaDatasetRepository egaDatasetRepository;
    private ProjectRepository projectRepository;
    private ProtocolRepository protocolRepository;
    private SampleGroupRepository sampleGroupRepository;
    private SampleRepository sampleRepository;
    private StudyRepository studyRepository;


    @Bean
    public List<SubmittableRepository<?>> submissionContentsRepositories() {
        return Arrays.asList(
                analysisRepository,
                assayDataRepository,
                assayRepository,
                egaDacPolicyRepository,
                egaDacRepository,
                egaDatasetRepository,
                projectRepository,
                protocolRepository,
                sampleGroupRepository,
                sampleRepository,
                studyRepository
        );
    }


}
