package uk.ac.ebi.subs.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.data.component.Archive;
import uk.ac.ebi.subs.data.submittable.*;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestRepoApplication.class)
public class SubmissionServiceTest {

    @Autowired
    SubmissionService submissionService;

    @Autowired
    SubmissionRepository submissionRepository;

    Submission testSub;

    @Before
    public void buildUp() {
        testSub = new Submission();
        testSub.getSubmitter().setEmail("test@example.ac.uk");
        testSub.getDomain().setName("testDomain" + Math.random());



        Project p = new Project();
        Study st = new Study();
        Sample sa = new Sample();
        Assay a = new Assay();
        AssayData ad = new AssayData();

        for (Submittable sub : Arrays.asList(p,st,sa,a,ad)){
            sub.setDomain(testSub.getDomain());
        }

        p.setTitle("Test project");
        p.setArchive(Archive.Usi);

        st.setTitle("Test Seq Project");
        st.setArchive(Archive.Ena);
        st.setProjectRef(p.asRef());

        sa.setTitle("Test sample");
        sa.setDescription("A mouflon");
        sa.setTaxonId(9938L);
        sa.setTaxon("Ovis aries musimon");
        sa.setArchive(Archive.Usi);

        a.setTitle("Test assay");
        a.setArchive(Archive.Ena);
        a.setSampleRef(sa.asRef());
        a.setStudyRef(st.asRef());

        ad.setTitle("Test assay data");
        ad.setAssayRef(a.asRef());


        testSub.getProjects().add(p);
        testSub.getStudies().add(st);
        testSub.getSamples().add(sa);
        testSub.getAssays().add(a);
        testSub.getAssayData().add(ad);


    }

    @After
    public void tearDown() {
        submissionRepository.delete(testSub.getId());
    }

    @Test
    public void storeSubmission() {
        submissionService.storeSubmission(testSub);

        assertSubmissionStored();
    }


    private void assertSubmissionStored() {
        Submission stored = submissionRepository.findOne(testSub.getId());

        assertThat("Submission stored", stored.getDomain().getName(), equalTo(testSub.getDomain().getName()));
    }


}
