package uk.ac.ebi.subs.frontend;

import org.apache.catalina.connector.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.subs.FrontendApplication;
import uk.ac.ebi.subs.data.submittable.Submission;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontendApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SubmissionControllerIT {

    @LocalServerPort
    private int port;

    private URL submit;
    private URL submissions;
    private TestRestTemplate template;

    Submission sub;

    @Before
    public void setUp() throws Exception {
        this.submit = new URL("http://localhost:" + port + "/submit/");
        this.submissions = new URL("http://localhost:" + port + "/submissions/");

        template = new TestRestTemplate();

        sub = new Submission();
        sub.getDomain().setName("exampleDomain");
        sub.getSubmitter().setEmail("test@example.ac.uk");
    }

    @Test
    public void doSubmit() {
        template.put(submit.toString(), sub);

//        ResponseEntity<ArrayList> response = template.getForEntity(submissions.toString(), ArrayList.class);

    }
}
