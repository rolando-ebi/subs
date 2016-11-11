package uk.ac.ebi.subs.processing;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProcessingCertificateEnvelope {
    String submissionId;
    List<ProcessingCertificate> processingCertificates = new ArrayList<>();

    public ProcessingCertificateEnvelope(String submissionId) {
        this.submissionId = submissionId;
    }
    public ProcessingCertificateEnvelope(String submissionId, List<ProcessingCertificate> processingCertificates) {
        this.submissionId = submissionId;
        this.processingCertificates = processingCertificates;
    }

    public ProcessingCertificateEnvelope() {
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public List<ProcessingCertificate> getProcessingCertificates() {
        return processingCertificates;
    }

    public void setProcessingCertificates(List<ProcessingCertificate> processingCertificates) {
        this.processingCertificates = processingCertificates;
    }

    @Override
    public String toString() {
        return "ProcessingCertificateEnvelope{" +
                "submissionId='" + submissionId + '\'' +
                ", processingCertificates=" + processingCertificates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingCertificateEnvelope that = (ProcessingCertificateEnvelope) o;
        return Objects.equals(submissionId, that.submissionId) &&
                Objects.equals(processingCertificates, that.processingCertificates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(submissionId, processingCertificates);
    }
}
