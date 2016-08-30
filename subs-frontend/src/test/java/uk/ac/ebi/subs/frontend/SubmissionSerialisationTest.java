package uk.ac.ebi.subs.frontend;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.FileCopyUtils;
import uk.ac.ebi.subs.FrontendApplication;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.data.submittable.Submission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontendApplication.class)
@JsonTest
public class SubmissionSerialisationTest {

    @Autowired
    private JacksonTester<Submission> json;

    @Test
    public void testSerialize() throws Exception {
        assertThat(this.json.write(sub)).isEqualToJson(exampleJson);
    }

    String exampleJson;
    Submission sub;

    @Before
    public void setUp() throws IOException {
        sub = new Submission();
        sub.getDomain().setName("exampleDomain");
        sub.getSubmitter().setEmail("test@example.ac.uk");
        sub.getSamples().add(new Sample());

        exampleJson = "{\"submitter\":{\"email\":\"test@example.ac.uk\"},\"domain\":{\"name\":\"exampleDomain\"}, \"samples\": [{}]}";
    }

    @Test
    public void testDeserialize() throws Exception {

        ObjectContent<Submission> deserializedSub = this.json.parse(exampleJson);
        Submission actualSub = sub;

        assertThat(actualSub.getDomain())
                .isEqualTo(sub.getDomain());
        assertThat(actualSub.getSubmitter())
                .isEqualTo(sub.getSubmitter());
        assertThat(actualSub.getSubmissionDate()).isEqualTo(sub.getSubmissionDate());
    }


}
