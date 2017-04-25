package uk.ac.ebi.subs.enaagent;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.EnaAgentApplication;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.component.*;
import uk.ac.ebi.subs.data.status.ProcessingStatusEnum;
import uk.ac.ebi.subs.data.submittable.Assay;
import uk.ac.ebi.subs.data.submittable.AssayData;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.processing.ProcessingCertificate;
import uk.ac.ebi.subs.processing.ProcessingCertificateEnvelope;
import uk.ac.ebi.subs.processing.SubmissionEnvelope;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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

    ObjectMapper objectMapper = new ObjectMapper();

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
    public void setUp() throws IOException {
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

        objectMapper.writeValue(System.out,as);

        as.setStudyRef((StudyRef) st.asRef());
        as.setTeam(team);

        System.out.println(objectMapper.writeValueAsString(as));

        ad = new AssayData();
        ad.setAlias("run1");
        ad.setArchive(Archive.Ena);
        ad.setAssayRef((AssayRef) as.asRef());
        ad.setTeam(team);

        arrayStudy = new Study();
        arrayStudy.setArchive(Archive.ArrayExpress);
        arrayStudy.setAlias("not to be accessioned here");
        arrayStudy.setTeam(team);

        sub = new Submission();
        sub.setTeam(team);
        sub.setId("this-is-a-fake-id");

        subEnv = new SubmissionEnvelope(sub);
        subEnv.getSamples().add(sa);
        subEnv.getStudies().add(st);
        subEnv.getAssays().add(as);
        subEnv.getAssayData().add(ad);
    }


}
