package uk.ac.ebi.subs.processing;


import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString
@EqualsAndHashCode
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

}
