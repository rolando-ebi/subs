package uk.ac.ebi.subs.processing;


import uk.ac.ebi.subs.data.component.Archive;
import uk.ac.ebi.subs.data.submittable.Submittable;

import java.util.Objects;

public class ProcessingCertificate {
    String submittableId;
    Archive archive;
    ProcessingStatus processingStatus;
    String accession;

    public ProcessingCertificate(Submittable submittable, Archive archive, ProcessingStatus processingStatus){
        this.submittableId = submittable.getId();
        this.archive = archive;
        this.processingStatus = processingStatus;
    }

    public ProcessingCertificate(Submittable submittable, Archive archive, ProcessingStatus processingStatus, String accession){
        this(submittable,archive,processingStatus);
        this.accession = accession;
    }


    public ProcessingCertificate() {
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getSubmittableId() {
        return submittableId;
    }

    public void setSubmittableId(String UUID) {
        this.submittableId = UUID;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    public ProcessingStatus getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(ProcessingStatus processingStatus) {
        this.processingStatus = processingStatus;
    }

    @Override
    public String toString() {
        return "ProcessingCertificate{" +
                "submittableId='" + submittableId + '\'' +
                ", archive=" + archive +
                ", processingStatus=" + processingStatus +
                ", accession='" + accession + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessingCertificate that = (ProcessingCertificate) o;
        return Objects.equals(submittableId, that.submittableId) &&
                archive == that.archive &&
                processingStatus == that.processingStatus &&
                Objects.equals(accession, that.accession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(submittableId, archive, processingStatus, accession);
    }
}
