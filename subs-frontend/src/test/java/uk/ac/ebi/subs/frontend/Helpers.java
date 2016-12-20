package uk.ac.ebi.subs.frontend;


import uk.ac.ebi.subs.data.FullSubmission;
import uk.ac.ebi.subs.data.component.Domain;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.processing.ProcessingStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Helpers {

    public static List<Sample> generateTestSamples() {
        List<Sample> samples = new ArrayList<>();
        Sample sample1 = new Sample();
        sample1.setAlias("d1");
        sample1.setDescription("Donor 1");
        sample1.setTaxon("Homo sapiens");
        sample1.setTaxonId(9606L);
        samples.add(sample1);

        Sample sample2 = new Sample();
        sample2.setAlias("d2");
        sample2.setDescription("Donor 2");
        sample2.setTaxon("Homo sapiens");
        sample2.setTaxonId(9606L);
        samples.add(sample2);

        return samples;
    }

    public static FullSubmission generateTestFullSubmission() {
        FullSubmission sub = new FullSubmission(generateTestSubmission());

        sub.setSamples(generateTestSamples());

        return sub;
    }

    public static Submission generateTestSubmission(){
        Submission sub = new Submission();
        Domain d = new Domain();
        sub.setId(UUID.randomUUID().toString());
        d.setName("my-domain");
        sub.setDomain(d);
        sub.setStatus(ProcessingStatus.Draft.name());
        return sub;
    }
}
