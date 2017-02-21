package uk.ac.ebi.subs.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.repository.model.Submission;
import uk.ac.ebi.subs.repository.repos.SubmissionRepository;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestRepoApplication.class)
public class SubmissionRepositoryTest {

    @Autowired
    SubmissionRepository submissionRepository;

    Submission testSub;

    @Before
    public void buildUp() {
        testSub = new Submission();
        testSub.getSubmitter().setEmail("test@example.ac.uk");
        testSub.getDomain().setName("testDomain" + Math.random());
        testSub.setId(UUID.randomUUID().toString());
    }

    @After
    public void tearDown() {
        submissionRepository.delete(testSub.getId());
    }

    @Test
    public void storeSubmission() {
        submissionRepository.save(testSub);

        assertSubmissionStored();
    }

    private void assertSubmissionStored() {
        Submission stored = submissionRepository.findOne(testSub.getId());
        assertThat("Submission stored", stored.getDomain().getName(), equalTo(testSub.getDomain().getName()));
    }

}
