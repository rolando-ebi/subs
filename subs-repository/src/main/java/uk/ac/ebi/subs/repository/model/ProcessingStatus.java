package uk.ac.ebi.subs.repository.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.hateoas.Identifiable;
import uk.ac.ebi.subs.data.status.ProcessingStatusEnum;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessingStatus extends uk.ac.ebi.subs.data.status.ProcessingStatus implements Identifiable<String> {

    public ProcessingStatus() {
    }

    public static ProcessingStatus createForSubmittable(StoredSubmittable storedSubmittable){
        ProcessingStatus processingStatus = new ProcessingStatus(ProcessingStatusEnum.Draft);

        processingStatus.setSubmissionId(storedSubmittable.getSubmission().getId());

        storedSubmittable.setProcessingStatus(processingStatus);

        return processingStatus;
    }

    public ProcessingStatus(ProcessingStatusEnum statusEnum) {
        super(statusEnum);
    }




    @Id
    private String id;

    @Version
    private Long version;
    @CreatedDate
    private Date createdDate;
    @LastModifiedDate
    private Date lastModifiedDate;

    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;

    private String submissionId;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }
}
