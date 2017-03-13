package uk.ac.ebi.subs.repository;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.data.component.Team;
import uk.ac.ebi.subs.repository.model.ProcessingStatus;
import uk.ac.ebi.subs.repository.model.Sample;
import uk.ac.ebi.subs.repository.model.Submission;
import uk.ac.ebi.subs.repository.repos.submittables.SampleRepository;
import uk.ac.ebi.subs.repository.repos.submittables.SubmittableRepository;
import uk.ac.ebi.subs.repository.security.AuthorizeUser;
import uk.ac.ebi.tsc.aap.client.model.Domain;
import uk.ac.ebi.tsc.aap.client.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestRepoApplication.class)
public class AuthorizeUserTest {

    private AuthorizeUser authorizeUser;
    @Autowired
    private List<SubmittableRepository<?>> submissionContentsRepositories;
    @Autowired
    private SampleRepository sampleRepository;


    @Test
    public void findCorrectTeamName() {
        String teamName = "testTeam";

        Team team = createTeam(teamName);

        Sample sample = createSample(team);

        sampleRepository.insert(sample);
        ProcessingStatus status = ProcessingStatus.createForSubmittable(sample);

        Team teamFromStatus = authorizeUser.processingStatusTeam(status);

        assertThat(teamFromStatus, is(equalTo(team)));
    }

    @Test
    public void userIsInTeam() {
        String teamName = "testTeam";

        User user = createUser(teamName);

        Team team = createTeam(teamName);
        Sample sample = createSample(team);

        sampleRepository.insert(sample);
        ProcessingStatus status = ProcessingStatus.createForSubmittable(sample);

        Boolean isAuthorised = authorizeUser.canUseProcessingStatus(user,status);

        assertThat(isAuthorised, is(equalTo(true)));
    }

    @Test
    public void userIsNotInTeam() {

        User user = createUser("testTeam");

        Team team = createTeam("bobbins");
        Sample sample = createSample(team);

        sampleRepository.insert(sample);
        ProcessingStatus status = ProcessingStatus.createForSubmittable(sample);

        Boolean isAuthorised = authorizeUser.canUseProcessingStatus(user,status);

        assertThat(isAuthorised, is(equalTo(false)));
    }

    private Team createTeam(String teamName) {
        Team team = new Team();
        team.setName(teamName);
        return team;
    }


    public Sample createSample(Team team) {
        Sample sample = new Sample();

        Submission submission = new Submission();
        submission.setId(UUID.randomUUID().toString());

        sample.setSubmission(submission);

        sample.setId(UUID.randomUUID().toString());
        sample.setTeam(team);

        return sample;
    }


    private User createUser(String... domainNames) {
        User user = new User();
        user.setDomains(new HashSet<>());
        for (String domainName : domainNames) {
            addDomainToUser(user, domainName);
        }
        return user;
    }

    private void addDomainToUser(User user, String domainName) {
        user.getDomains().add(createDomain(domainName));
    }

    private Domain createDomain(String domainName) {
        Domain domain = new Domain();
        domain.setDomainName(domainName);
        return domain;
    }

    @Before
    public void buildUp() {
        tearDown();
        authorizeUser = new AuthorizeUser(submissionContentsRepositories);
    }

    @After
    public void tearDown() {
        submissionContentsRepositories.forEach(repo -> repo.deleteAll());
    }


}
