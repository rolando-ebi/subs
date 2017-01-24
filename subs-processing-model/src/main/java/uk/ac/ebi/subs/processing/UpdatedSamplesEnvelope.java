package uk.ac.ebi.subs.processing;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.ac.ebi.subs.data.submittable.Sample;

import java.util.List;

@ToString
@EqualsAndHashCode
public class UpdatedSamplesEnvelope {
    private String submissionId;
    private List<Sample> updatedSamples;

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public List<Sample> getUpdatedSamples() {
        return updatedSamples;
    }

    public void setUpdatedSamples(List<Sample> updatedSamples) {
        this.updatedSamples = updatedSamples;
    }
}
