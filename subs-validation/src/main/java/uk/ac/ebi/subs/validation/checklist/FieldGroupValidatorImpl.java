package uk.ac.ebi.subs.validation.checklist;

import org.apache.commons.lang.StringUtils;

import uk.ac.ebi.ena.sra.xml.ChecklistType;
import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationMessage;
import uk.ac.ebi.subs.validation.ValidationResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FieldGroupValidatorImpl extends AbstractAttributeValidator implements FieldValidator {
    Set<FieldValidatorImpl> fieldValidatorSet = new HashSet<FieldValidatorImpl>();
    ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.Enum restrictionType = ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.ANY_NUMBER_OR_NONE_OF_THE_FIELDS;
    private String name;
    private String description;


    public FieldGroupValidatorImpl(ChecklistType.DESCRIPTOR.FIELDGROUP fieldGroup, Set<FieldValidatorImpl> fieldValidatorSet) {
        this(fieldGroup.getNAME(), fieldGroup.getDESCRIPTION(), fieldGroup.getRestrictionType(), fieldValidatorSet);
    }

    public FieldGroupValidatorImpl(String name, String description, ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.Enum restrictionType, Set<FieldValidatorImpl> fieldValidatorSet) {
        super(name);
        this.name = name;
        this.description = description;
        this.restrictionType = restrictionType;
        this.fieldValidatorSet = fieldValidatorSet;
    }

    @Override
    public boolean validate(Attribute attribute, ValidationResult validationResult) throws ValidationException {
        return true;
    }

    @Override
    public boolean validate(List<Attribute> attributeList, ValidationResult validationResult) throws ValidationException {
        final boolean validate = super.validate(attributeList, validationResult);
        return validate;
    }

    public boolean validate(ValidationResult validationResult) throws ValidationException {
        boolean validationStatus = true;
        int fieldValidationCount = 0;
        for (FieldValidatorImpl fieldValidator : fieldValidatorSet) {
            if (fieldValidator.isValidated()) fieldValidationCount++;
        }

        final String fieldString = StringUtils.join(fieldValidatorSet, ",");

        if (restrictionType.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.AT_LEAST_ONE_OF_THE_FIELDS) && (fieldValidationCount == 0)) {
            validationResult.append(ValidationMessage.error("ERAM.2.0.1", name, fieldString));
            validationStatus = false;
        } else if (restrictionType.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.ONE_OF_THE_FIELDS) && (!(fieldValidationCount == 1))) {
            validationResult.append(ValidationMessage.error("ERAM.2.0.2", name, fieldString));
            validationStatus = false;
        } else if (restrictionType.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.ONE_OR_NONE_OF_THE_FIELDS) && (fieldValidationCount <= 1)) {
            validationResult.append(ValidationMessage.error("ERAM.2.0.3", name, fieldString));
            validationStatus = false;
        }
        return validationStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.Enum getRestrictionType() {
        return restrictionType;
    }

    public void setRestrictionType(ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.Enum restrictionType) {
        this.restrictionType = restrictionType;
    }

    public Set<FieldValidatorImpl> getFieldValidatorList() {
        return fieldValidatorSet;
    }

    public void setFieldValidatorList(Set<FieldValidatorImpl> fieldValidatorList) {
        this.fieldValidatorSet = fieldValidatorList;
    }

    public void reset() {
    }
}
