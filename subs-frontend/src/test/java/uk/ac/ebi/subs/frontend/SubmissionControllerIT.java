package uk.ac.ebi.subs.frontend;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.subs.FrontendApplication;

import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.processing.ProcessingStatus;
import uk.ac.ebi.subs.processing.SubmissionEnvelope;
import uk.ac.ebi.subs.messaging.Queues;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.submittable.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubmissionControllerIT {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @LocalServerPort
    private int port;

    private URI submissionsUri;

    private List<Submission> submissionsReceived;

    @RabbitListener(queues = Queues.SUBMISSION_DISPATCHER)
    public void listenForSubmission(SubmissionEnvelope submissionEnvelope) {
        System.out.println("Received a newly created submission: accession = " + submissionEnvelope.getSubmission().getId());
        this.submissionsReceived.add(submissionEnvelope.getSubmission());
    }


    @Autowired SubmissionRepository submissionRepository;
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
    @Autowired EgaDatasetRepository egaDatasetRepository;
    @Autowired ProjectRepository projectRepository;
    @Autowired ProtocolRepository protocolRepository;
    @Autowired SampleRepository sampleRepository;
    @Autowired SampleGroupRepository sampleGroupRepository;
    @Autowired StudyRepository studyRepository;

    @Autowired RestTemplate restTemplate;

    private Submission sub;

    private List<CrudRepository> crudRepos(){
        return Arrays.asList(
                submissionRepository,
                analysisRepository,
                assayRepository,
                assayDataRepository,
                egaDacRepository,
                egaDacPolicyRepository,
                egaDatasetRepository,
                projectRepository,
                protocolRepository,
                sampleRepository,
                sampleGroupRepository,
                studyRepository
        );
    }

    private void deleteAllRepos(){
        //nuke the site from orbit
        crudRepos().forEach(cr -> cr.deleteAll());
    }

    @Before
    public void setUp() throws Exception {
        this.submissionsUri = URI.create("http://localhost:" + this.port + "/api/submissions");

        deleteAllRepos();

        sub = new Submission();
        sub.setStatus(ProcessingStatus.Draft.name());
        sub.getDomain().setName("integrationTestExampleDomain."+UUID.randomUUID().toString());
        sub.getSubmitter().setEmail("test@example.ac.uk");

        this.submissionsReceived = new ArrayList<>();
    }



    @After
    public void tearDown() {
        deleteAllRepos();
    }



    @Test
    public void doSubmit() throws URISyntaxException, InterruptedException {
        ResponseEntity<Void> response = restTemplate.postForEntity(submissionsUri.toString(), sub, Void.class);

        assertThat(response.getStatusCodeValue(), is(equalTo(201)));
        assertThat(response.getHeaders().getLocation(),notNullValue());


        URI location = response.getHeaders().getLocation();

        ResponseEntity<Resource> submissionResource = restTemplate.postForEntity(
                submissionsUri.toString(), sub, Resource.class);




        Thread.sleep(1000);

        assertThat(submissionsReceived.size(),equalTo(0));
    }
}
