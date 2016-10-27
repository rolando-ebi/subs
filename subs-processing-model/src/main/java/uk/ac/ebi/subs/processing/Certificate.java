package uk.ac.ebi.subs.processing;


import uk.ac.ebi.subs.data.component.Archive;
import uk.ac.ebi.subs.data.submittable.Submittable;

import java.util.Date;
import java.util.Objects;

public class Certificate {
    String uuid;
    Archive archive;
    ProcessingStatus processingStatus;
    String accession;

    public Certificate(Submittable submittable,Archive archive, ProcessingStatus processingStatus){
        this.uuid = submittable.getAlias(); //TODO make it a UUID
        this.archive = archive;
        this.processingStatus = processingStatus;
    }

    public Certificate(Submittable submittable,Archive archive, ProcessingStatus processingStatus, String accession){
        this.uuid = submittable.getAlias(); //TODO make it a UUID
        this.archive = archive;
        this.processingStatus = processingStatus;
        this.accession = accession;
    }

    public Certificate() {
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String UUID) {
        this.uuid = UUID;
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
        return "Certificate{" +
                "uuid='" + uuid + '\'' +
                ", archive=" + archive +
                ", processingStatus=" + processingStatus +
                ", accession='" + accession + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Certificate that = (Certificate) o;
        return Objects.equals(uuid, that.uuid) &&
                archive == that.archive &&
                processingStatus == that.processingStatus &&
                Objects.equals(accession, that.accession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, archive, processingStatus, accession);
    }
}
