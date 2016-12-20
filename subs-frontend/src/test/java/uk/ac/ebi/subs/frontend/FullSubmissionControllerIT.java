package uk.ac.ebi.subs.frontend;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.TypeReferences;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import uk.ac.ebi.subs.FrontendApplication;
import uk.ac.ebi.subs.data.FullSubmission;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.frontend.handlers.SubmissionEventHandler;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.submittable.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullSubmissionControllerIT {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestOperations restOperations;

    @LocalServerPort
    private int port;
    private URI submissionsUri;


    @Autowired
    SubmissionRepository submissionRepository;
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
    RestTemplate restTemplate;

    @Autowired
    SubmissionEventHandler submissionEventHandler;

    private FullSubmission sub;
    private Map<String,Sample> expectedSamplesByAlias;

    private List<CrudRepository> crudRepos() {
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

    private void deleteAllRepos() {
        //nuke the site from orbit
        crudRepos().forEach(cr -> cr.deleteAll());
    }

    @Before
    public void setUp() throws Exception {
        this.submissionsUri = URI.create("http://localhost:" + this.port + "/api/fullSubmissions");

        deleteAllRepos();

        sub = Helpers.generateTestFullSubmission();

        expectedSamplesByAlias = new HashMap<>();
        sub.getSamples().forEach(s -> expectedSamplesByAlias.put(s.getAlias(),s));
    }


    @After
    public void tearDown() {
        deleteAllRepos();
    }


    @Test
    public void doSubmit() throws URISyntaxException, InterruptedException {
        //post a full submission
        RequestEntity<FullSubmission> postRequest = RequestEntity.post(submissionsUri).accept(HAL_JSON).body(sub);
        ResponseEntity<Void> responseToPost = restOperations.exchange(postRequest, Void.class);


        assertThat(responseToPost.getStatusCodeValue(), is(equalTo(201)));
        assertThat(responseToPost.getHeaders().getLocation(), notNullValue());

        URI submissionResourceURI = responseToPost.getHeaders().getLocation();

        //get a submission
        RequestEntity<Void> subGetRequest = RequestEntity.get(submissionResourceURI).accept(HAL_JSON).build();
        ResponseEntity<Resource<Submission>> responseToGet = restOperations.exchange(subGetRequest, new TypeReferences.ResourceType<Submission>() {
        });

        assertThat(responseToGet.getStatusCodeValue(), is(equalTo(200)));
        Resource<Submission> submissionResource = responseToGet.getBody();


        assertThat(submissionResource.getLink("self"), notNullValue());
        Link samplesLink = submissionResource.getLink("submissionSamples");
        assertThat(samplesLink, notNullValue());

        //follow rel to the samples within

        RequestEntity<Void> samplesGetRequest = RequestEntity.get(URI.create(samplesLink.getHref())).accept(HAL_JSON).build();
        PagedResources<Resource<Sample>> sampleResources = restOperations.exchange(samplesGetRequest, new TypeReferences.PagedResourcesType<Resource<Sample>>() {
        }).getBody();

        assertThat(sampleResources, iterableWithSize(sub.getSamples().size()));

        sampleResources.forEach(sampleResource -> {
            Sample s = sampleResource.getContent();
            Link selfLink = sampleResource.getLink("self");

            assertThat(s,notNullValue());
            assertThat(selfLink,notNullValue());
            assertThat(s.getAlias(),notNullValue());



            assertThat(expectedSamplesByAlias.containsKey(s.getAlias()),is(true));

            Sample expectedSample = expectedSamplesByAlias.remove(s.getAlias());

            assertThat(s.getDescription(),is(expectedSample.getDescription()));

        });

        assertThat(expectedSamplesByAlias.entrySet(),iterableWithSize(0));


    }
}
