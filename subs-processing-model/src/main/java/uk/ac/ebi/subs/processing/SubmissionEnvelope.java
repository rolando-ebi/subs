package uk.ac.ebi.subs.processing;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.DBRef;

import uk.ac.ebi.subs.data.FullSubmission;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.component.SampleRef;
import uk.ac.ebi.subs.data.submittable.Sample;

import java.util.*;


@ToString
@EqualsAndHashCode
public class SubmissionEnvelope {

    private String id;

    private FullSubmission submission;

    private Set<SampleRef> supportingSamplesRequired = new HashSet<>();
    private List<Sample> supportingSamples = new ArrayList<>();



    public SubmissionEnvelope() {};

    public  SubmissionEnvelope(FullSubmission submission){
        this.id = submission.getId();
        this.submission = submission;
    }


    public FullSubmission getSubmission() {
        return submission;
    }

    public void setSubmission(FullSubmission submission) {
        this.submission = submission;
    }

    public Set<SampleRef> getSupportingSamplesRequired() {
        return supportingSamplesRequired;
    }

    public void setSupportingSamplesRequired(Set<SampleRef> supportingSamplesRequired) {
        this.supportingSamplesRequired = supportingSamplesRequired;
    }

    public List<Sample> getSupportingSamples() {
        return supportingSamples;
    }

    public void setSupportingSamples(List<Sample> supportingSamples) {
        this.supportingSamples = supportingSamples;
    }
}
