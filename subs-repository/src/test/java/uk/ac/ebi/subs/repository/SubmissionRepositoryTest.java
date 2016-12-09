package uk.ac.ebi.subs.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.component.*;
import uk.ac.ebi.subs.data.submittable.*;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
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
        p.setId(UUID.randomUUID().toString());

        st.setTitle("Test Seq Project");
        st.setArchive(Archive.Ena);
        st.setProjectRef((ProjectRef) p.asRef());
        st.setId(UUID.randomUUID().toString());

        sa.setTitle("Test sample");
        sa.setDescription("A mouflon");
        sa.setTaxonId(9938L);
        sa.setTaxon("Ovis aries musimon");
        sa.setArchive(Archive.Usi);
        sa.setId(UUID.randomUUID().toString());

        a.setTitle("Test assay");
        a.setArchive(Archive.Ena);
        a.getSampleUses().add(new SampleUse( (SampleRef) sa.asRef() ));
        a.setStudyRef((StudyRef) st.asRef());
        a.setId(UUID.randomUUID().toString());

        ad.setTitle("Test assay data");
        ad.setAssayRef((AssayRef) a.asRef());
        ad.setId(UUID.randomUUID().toString());


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
        submissionRepository.save(testSub);

        assertSubmissionStored();
    }

    private void assertSubmissionStored() {
        Submission stored = submissionRepository.findOne(testSub.getId());
        assertThat("Submission stored", stored.getDomain().getName(), equalTo(testSub.getDomain().getName()));
    }

}
