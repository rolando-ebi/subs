package uk.ac.ebi.subs.processing;


import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.ac.ebi.subs.data.validation.EntityValidationOutcome;
import uk.ac.ebi.subs.data.validation.FieldValidationOutcome;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
public class ValidationOutcomeEnvelope {

    public ValidationOutcomeEnvelope(String submittableId, Long versionNumberValidated) {
        this.submittableId = submittableId;
        this.versionNumberValidated = versionNumberValidated;
    }

    public ValidationOutcomeEnvelope() {
    }

    private String submittableId;
    private Long versionNumberValidated;

    private List<FieldValidationOutcome> fieldOutcomes = new ArrayList<>();
    private List<EntityValidationOutcome> entityOutcomes = new ArrayList<>();

    public String getSubmittableId() {
        return submittableId;
    }

    public void setSubmittableId(String submittableId) {
        this.submittableId = submittableId;
    }

    public Long getVersionNumberValidated() {
        return versionNumberValidated;
    }

    public void setVersionNumberValidated(Long versionNumberValidated) {
        this.versionNumberValidated = versionNumberValidated;
    }

    public List<FieldValidationOutcome> getFieldOutcomes() {
        return fieldOutcomes;
    }

    public void setFieldOutcomes(List<FieldValidationOutcome> fieldOutcomes) {
        this.fieldOutcomes = fieldOutcomes;
    }

    public List<EntityValidationOutcome> getEntityOutcomes() {
        return entityOutcomes;
    }

    public void setEntityOutcomes(List<EntityValidationOutcome> entityOutcomes) {
        this.entityOutcomes = entityOutcomes;
    }
}
