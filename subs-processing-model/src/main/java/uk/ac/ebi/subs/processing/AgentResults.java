package uk.ac.ebi.subs.processing;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentResults that = (AgentResults) o;
        return Objects.equals(submissionUuid, that.submissionUuid) &&
                Objects.equals(certificates, that.certificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(submissionUuid, certificates);
    }
}
