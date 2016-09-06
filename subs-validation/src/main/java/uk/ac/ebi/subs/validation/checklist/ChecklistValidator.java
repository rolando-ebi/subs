package uk.ac.ebi.subs.validation.checklist;

import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationResult;

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

    String getName();

    public void reset();

}