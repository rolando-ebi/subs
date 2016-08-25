package uk.ac.ebi.ena.sra.validation.checklist2;

import uk.ac.ebi.embl.api.validation.ValidationResult;

import java.util.List;

public interface ValidationCheck {
    /**
     * Validata a list of attributes
     *
     * @return true if the list of attributes validates successfully
     */
    public Boolean validate(List<Attribute> attributes, List<ValidationResult> validationResultList);
}
