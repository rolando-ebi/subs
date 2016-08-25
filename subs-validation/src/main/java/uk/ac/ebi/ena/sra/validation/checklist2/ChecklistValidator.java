package uk.ac.ebi.ena.sra.validation.checklist2;

import uk.ac.ebi.embl.api.validation.ValidationException;
import uk.ac.ebi.embl.api.validation.ValidationResult;

import java.util.List;

/**
 * Use to validate SRA attributes using checklists
 */
public interface ChecklistValidator {

    /**
     * Validate an SRA object using checklists
     */
    boolean validate(List<Attribute> attributes, ValidationResult validationResult) throws ValidationException;

    boolean validate(Attribute attribute, ValidationResult validationResult) throws ValidationException;

    String getId();

    public void reset();

}