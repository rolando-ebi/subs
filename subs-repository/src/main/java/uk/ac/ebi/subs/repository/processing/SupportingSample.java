package uk.ac.ebi.subs.repository.processing;


import uk.ac.ebi.subs.data.submittable.Sample;

public class SupportingSample {

    String submissionId;
    Sample sample;

    public SupportingSample(){

    }
    public SupportingSample(String submissionId, Sample sample){
        this.submissionId = submissionId;
        this.sample = sample;
    }

    public Sample getSample() {
        return sample;
    }

    public void setSample(Sample sample) {
        this.sample = sample;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }
}
