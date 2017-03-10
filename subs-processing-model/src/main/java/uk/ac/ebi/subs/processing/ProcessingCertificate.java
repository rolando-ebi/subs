package uk.ac.ebi.subs.processing;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.ac.ebi.subs.data.component.Archive;
import uk.ac.ebi.subs.data.status.ProcessingStatusEnum;
import uk.ac.ebi.subs.data.submittable.Submittable;

@ToString
@EqualsAndHashCode
public class ProcessingCertificate {
    private String submittableId;
    private Archive archive;
    private ProcessingStatusEnum processingStatus;
    private String accession;
    private String message;

    public ProcessingCertificate(Submittable submittable, Archive archive, ProcessingStatusEnum processingStatus) {
        this.submittableId = submittable.getId();
        this.archive = archive;
        this.processingStatus = processingStatus;
    }

    public ProcessingCertificate(Submittable submittable, Archive archive, ProcessingStatusEnum processingStatus, String accession) {
        this(submittable, archive, processingStatus);
        this.accession = accession;
    }


    public ProcessingCertificate() {
    }

    public String getSubmittableId() {
        return submittableId;
    }

    public void setSubmittableId(String submittableId) {
        this.submittableId = submittableId;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }

    public ProcessingStatusEnum getProcessingStatus() {
        return processingStatus;
    }

    public void setProcessingStatus(ProcessingStatusEnum processingStatus) {
        this.processingStatus = processingStatus;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
