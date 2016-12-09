package uk.ac.ebi.subs.repository.cascadesave;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.submittable.Assay;
import uk.ac.ebi.subs.data.submittable.Sample;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CascadingMongoEventListener.class)
@EnableMongoRepositories(basePackages = "uk.ac.ebi.subs.repository.submittable")
@EnableAutoConfiguration
public class CascadingMongoEventListenerTest {

    @Autowired
    MongoOperations mongoOperations;

    Submission submission;
    List<Sample> samples;
    List<Assay> assays;

    @Before
    public void cleanCollections() {
        mongoOperations.dropCollection(Submission.class);
        mongoOperations.dropCollection(Sample.class);

        // Defining a submission
        submission = new Submission();
        submission.setId(UUID.randomUUID().toString());

        // Defining 3 samples
        Sample sample1 = new Sample();
        Sample sample2 = new Sample();
        Sample sample3 = new Sample();
        samples = Arrays.asList(sample1, sample2, sample3);
        samples.forEach(s -> {
            s.setId(UUID.randomUUID().toString());
            s.setDescription("This is a description for sample [" + s.getId() + "]");
        });
        submission.setSamples(samples);

        // Defining 2 assays
        Assay assay1 = new Assay();
        Assay assay2 = new Assay();
        assays = Arrays.asList(assay1, assay2);
        assay1.setId(UUID.randomUUID().toString());
        assay1.setDescription("This is a description for assay [" + assay1.getId() + "]");
        assay2.setId(UUID.randomUUID().toString());
        assay2.setDescription("This is a description for assay [" + assay2.getId() + "]");
        submission.setAssays(assays);
    }

    @Test
    public void testCascadeSave() {
        mongoOperations.save(submission);

        List<Submission> submissions = mongoOperations.findAll(Submission.class);
        assertEquals(submissions.size(), 1);
        assertTrue(submissions.contains(submission));

        Submission savedSubmission = submissions.get(0);
        assertEquals(savedSubmission.getSamples().size(), 3);
        assertNotNull(savedSubmission.getSamples().get(0));
        assertEquals(submission.getSamples(), savedSubmission.getSamples());

        List<Sample> savedSamples = mongoOperations.findAll(Sample.class);
        assertEquals(samples, savedSamples);
    }

}