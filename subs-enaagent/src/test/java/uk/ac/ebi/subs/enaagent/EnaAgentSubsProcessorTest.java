package uk.ac.ebi.subs.enaagent;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.EnaAgentApplication;
import uk.ac.ebi.subs.data.FullSubmission;
import uk.ac.ebi.subs.data.component.*;
import uk.ac.ebi.subs.data.status.ProcessingStatusEnum;
import uk.ac.ebi.subs.data.submittable.Assay;
import uk.ac.ebi.subs.data.submittable.AssayData;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.processing.ProcessingCertificate;
import uk.ac.ebi.subs.processing.ProcessingCertificateEnvelope;
import uk.ac.ebi.subs.processing.SubmissionEnvelope;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EnaAgentApplication.class)
public class EnaAgentSubsProcessorTest {

    SubmissionEnvelope subEnv;
    FullSubmission sub;
    Sample sa;
    Study st;
    Assay as;
    AssayData ad;

    Study arrayStudy;

    @Autowired
    EnaAgentSubmissionsProcessor processor;

    @Test
    public void test() {
        ProcessingCertificateEnvelope processingCertificateEnvelope = processor.processSubmission(subEnv);
        List<ProcessingCertificate> certs = processingCertificateEnvelope.getProcessingCertificates();

        String processedStatus = ProcessingStatusEnum.Completed.name();

        assertThat("study accessioned", st.getAccession(), startsWith("ENA-STU-"));


        assertThat("assay accessioned", as.getAccession(), startsWith("ENA-EXP-"));


        assertThat("assay data accessioned", ad.getAccession(), startsWith("ENA-RUN-"));


        assertThat("array study untouched", arrayStudy.getAccession(), nullValue());


        assertThat("sample reference in assay", as.getSampleUses().get(0).getSampleRef().getAccession(), equalTo(sa.getAccession()));
        assertThat("study reference in assay", as.getStudyRef().getAccession(), equalTo(st.getAccession()));
        assertThat("assay reference in assay data ", ad.getAssayRef().getAccession(), equalTo(as.getAccession()));

        assertThat("correct certs",
                certs,
                containsInAnyOrder(
                        new ProcessingCertificate(st, Archive.Ena, ProcessingStatusEnum.Completed, st.getAccession()),
                        new ProcessingCertificate(as, Archive.Ena, ProcessingStatusEnum.Completed, as.getAccession()),
                        new ProcessingCertificate(ad, Archive.Ena, ProcessingStatusEnum.Completed, ad.getAccession())
                )

        );
        assertThat("correct submission id", processingCertificateEnvelope.getSubmissionId(), equalTo(sub.getId()));
    }


    @Before
    public void setUp() {
        Team team = new Team();
        team.setName("test team");

        sa = new Sample();
        sa.setAlias("bob");
        sa.setAccession("S1");
        sa.setArchive(Archive.Usi);
        sa.setTeam(team);

        st = new Study();
        st.setArchive(Archive.Ena);
        st.setAlias("study1");
        st.setTeam(team);

        as = new Assay();
        as.setArchive(Archive.Ena);
        as.setAlias("exp1");
        as.getSampleUses().add(new SampleUse((SampleRef) sa.asRef()));

        as.setStudyRef((StudyRef) st.asRef());
        as.setTeam(team);

        ad = new AssayData();
        ad.setAlias("run1");
        ad.setArchive(Archive.Ena);
        ad.setAssayRef((AssayRef) as.asRef());
        ad.setTeam(team);

        arrayStudy = new Study();
        arrayStudy.setArchive(Archive.ArrayExpress);
        arrayStudy.setAlias("not to be accessioned here");
        arrayStudy.setTeam(team);

        sub = new FullSubmission();
        sub.setTeam(team);
        sub.getSamples().add(sa);
        sub.getStudies().add(st);
        sub.getAssays().add(as);
        sub.getAssayData().add(ad);
        sub.setId("this-is-a-fake-id");

        subEnv = new SubmissionEnvelope(sub);
    }


}
