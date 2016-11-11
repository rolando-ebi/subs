package uk.ac.ebi.subs.processing;


import uk.ac.ebi.subs.data.submittable.Sample;

import java.util.List;

public class UpdatedSamplesEnvelope {
    String submissionId;
    List<Sample> updatedSamples;

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
