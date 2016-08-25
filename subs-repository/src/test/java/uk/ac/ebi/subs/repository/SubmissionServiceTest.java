package uk.ac.ebi.subs.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.data.component.Realm;
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

        Project p = new Project();
        Study st = new Study();
        Sample sa = new Sample();
        Assay a = new Assay();
        AssayData ad = new AssayData();

        p.setTitle("Test project");
        p.setRealm(Realm.Usi);

        st.setTitle("Test Seq Project");
        st.setRealm(Realm.Sequencing);
        st.setProjectRef(p.asLink());

        sa.setTitle("Test sample");
        sa.setDescription("A mouflon");
        sa.setTaxonId(9938);
        sa.setTaxon("Ovis aries musimon");
        sa.setRealm(Realm.Usi);

        a.setTitle("Test assay");
        a.setRealm(Realm.Sequencing);
        a.setSampleRef(sa.asLink());
        a.setStudyRef(st.asLink());

        ad.setTitle("Test assay data");
        ad.setAssayRef(a.asLink());

        testSub.getSubmitter().setEmail("test@example.ac.uk");
        testSub.getDomain().setName("testDomain" + Math.random());
        testSub.allSubmissionItems().addAll(Arrays.asList(
                p, st, sa, a, ad
        ));
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
