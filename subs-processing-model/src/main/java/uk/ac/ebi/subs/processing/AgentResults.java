package uk.ac.ebi.subs.processing;


import java.util.ArrayList;
import java.util.List;

public class AgentResults {
    String submissionUuid;
    List<Certificate> certificates = new ArrayList<>();

    public AgentResults(String submissionUuid) {
        this.submissionUuid = submissionUuid;
    }
    public AgentResults(String submissionUuid, List<Certificate> certificates) {
        this.submissionUuid = submissionUuid;
        this.certificates = certificates;
    }

    public AgentResults() {
    }

    public String getSubmissionUuid() {
        return submissionUuid;
    }

    public void setSubmissionUuid(String submissionUuid) {
        this.submissionUuid = submissionUuid;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    @Override
    public String toString() {
        return "AgentResults{" +
                "submissionUuid='" + submissionUuid + '\'' +
                ", certificates=" + certificates +
                '}';
    }
}
