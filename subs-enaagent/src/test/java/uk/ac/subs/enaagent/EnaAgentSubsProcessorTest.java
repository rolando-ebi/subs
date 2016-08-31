package uk.ac.subs.enaagent;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.data.component.Realm;
import uk.ac.ebi.subs.data.submittable.*;
import uk.ac.subs.EnaAgentApplication;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EnaAgentApplication.class)
public class EnaAgentSubsProcessorTest {

    Submission sub;
    Sample sa;
    Study st;
    Assay as;
    AssayData ad;

    Study arrayStudy;

    @Autowired
    EnaAgentSubmissionsProcessor processor;

    @Test
    public void test(){
        processor.processSubmission(sub);

        String processedStatus = "processed";

        assertThat("study accessioned", st.getAccession(), startsWith("ENA-STU-"));
        assertThat("study status", st.getStatus(),equalTo(processedStatus));

        assertThat("assay accessioned", as.getAccession(), startsWith("ENA-EXP-"));
        assertThat("assay status", as.getStatus(),equalTo(processedStatus));

        assertThat("assay data accessioned", ad.getAccession(), startsWith("ENA-RUN-"));
        assertThat("assay data status", ad.getStatus(),equalTo(processedStatus));

        assertThat("array study untouched", arrayStudy.getAccession(),nullValue());
        assertThat("array study status is null", arrayStudy.getStatus(),nullValue());

        assertThat("sample reference in assay", as.getSampleRef().getAccession(), equalTo(sa.getAccession()));
        assertThat("study reference in assay", as.getStudyRef().getAccession(), equalTo(st.getAccession()));
        assertThat("assay reference in assay data ", ad.getAssayRef().getAccession(), equalTo(as.getAccession()));


    }


    @Before
    public void setUp(){
        sa = new Sample();
        sa.setAlias("bob");
        sa.setAccession("S1");
        sa.setRealm(Realm.Usi);

        st = new Study();
        st.setRealm(Realm.Sequencing);
        st.setAlias("study1");

        as = new Assay();
        as.setRealm(Realm.Sequencing);
        as.setAlias("exp1");
        as.setSampleRef(sa.asLink());
        as.setStudyRef(st.asLink());

        ad = new AssayData();
        ad.setAlias("run1");
        ad.setRealm(Realm.Sequencing);
        ad.setAssayRef(as.asLink());

        arrayStudy = new Study();
        arrayStudy.setRealm(Realm.Arrays);
        arrayStudy.setAlias("not to be accessioned here");

        sub = new Submission();
        sub.getSamples().add(sa);
        sub.getStudies().add(st);
        sub.getAssays().add(as);
        sub.getAssayData().add(ad);
    }



}
