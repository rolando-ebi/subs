package uk.ac.ebi.subs.enaagent;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.SubmissionEnvelope;
import uk.ac.ebi.subs.data.component.*;
import uk.ac.ebi.subs.data.submittable.*;
import uk.ac.ebi.subs.EnaAgentApplication;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EnaAgentApplication.class)
public class EnaAgentSubsProcessorTest {

    SubmissionEnvelope subEnv;
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
        processor.processSubmission(subEnv);

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
        Domain domain = new Domain();
        domain.setName("test domain");

        sa = new Sample();
        sa.setAlias("bob");
        sa.setAccession("S1");
        sa.setArchive(Archive.Usi);
        sa.setDomain(domain);

        st = new Study();
        st.setArchive(Archive.Ena);
        st.setAlias("study1");
        st.setDomain(domain);

        as = new Assay();
        as.setArchive(Archive.Ena);
        as.setAlias("exp1");
        as.setSampleRef((SampleRef) sa.asRef());
        as.setStudyRef((StudyRef) st.asRef());
        as.setDomain(domain);

        ad = new AssayData();
        ad.setAlias("run1");
        ad.setArchive(Archive.Ena);
        ad.setAssayRef((AssayRef) as.asRef());
        ad.setDomain(domain);

        arrayStudy = new Study();
        arrayStudy.setArchive(Archive.ArrayExpress);
        arrayStudy.setAlias("not to be accessioned here");
        arrayStudy.setDomain(domain);

        sub = new Submission();
        sub.setDomain(domain);
        sub.getSamples().add(sa);
        sub.getStudies().add(st);
        sub.getAssays().add(as);
        sub.getAssayData().add(ad);

        subEnv = new SubmissionEnvelope(sub);
    }



}
