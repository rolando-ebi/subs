package uk.ac.ebi.subs.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.repository.model.Sample;
import uk.ac.ebi.subs.repository.model.Submission;
import uk.ac.ebi.subs.repository.repos.SubmissionRepository;
import uk.ac.ebi.subs.repository.repos.submittables.SampleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestRepoApplication.class)
public class SampleRepositoryTest {

    @Autowired
    SubmissionRepository submissionRepository;

    @Autowired
    SampleRepository sampleRepository;

    Submission testSub;

    List<Sample> samples = new ArrayList<>();

    @Before
    public void buildUp() {
        tearDown();

        testSub = new Submission();
        testSub.getSubmitter().setEmail("test@example.ac.uk");
        testSub.getDomain().setName("testDomain");
        testSub.setId(UUID.randomUUID().toString());

        samples.add(new Sample());
        samples.add(new Sample());

        samples.get(0).setAlias("one");
        samples.get(1).setAlias("two");

        samples.forEach(s -> s.setDomain(testSub.getDomain()));

    }

    @After
    public void tearDown() {
        submissionRepository.deleteAll();
        sampleRepository.deleteAll();
    }

    @Test
    public void test() {
        submissionRepository.insert(testSub);
        samples.forEach(s -> s.setSubmission(testSub));

        sampleRepository.save(samples);

        PageRequest pageRequest = new PageRequest(0,10);

        assertThat(sampleRepository.findBySubmissionId(testSub.getId(),pageRequest).getTotalElements(),is(equalTo((long)samples.size())));

        assertThat(sampleRepository.submittablesInDomain(testSub.getDomain().getName(),pageRequest).getTotalElements(),is(equalTo((long)samples.size())));

        assertThat(sampleRepository.findFirstByDomainNameAndAliasOrderByCreatedDateDesc(testSub.getDomain().getName(), "two"),notNullValue());

    }

}
