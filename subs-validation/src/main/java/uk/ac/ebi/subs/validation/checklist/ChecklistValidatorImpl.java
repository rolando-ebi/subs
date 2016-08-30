package uk.ac.ebi.subs.validation.checklist;

import uk.ac.ebi.ena.sra.xml.ChecklistType;
import uk.ac.ebi.subs.validation.Severity;
import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationMessageManager;
import uk.ac.ebi.subs.validation.ValidationResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Condition parser. The condition consists of the already defined grammar and we confirm that
 * LABELs are used in the expression. If a LABEL is defined in a condition expression that does not
 * exist in the checklist then just ignore the condition (this is a checklist error that should not
 * stop the submission). Otherwise the condition should be checked.
 */

public class ChecklistValidatorImpl extends AbstractAttributeValidator implements FieldValidator {
    Map<String, FieldValidatorImpl> fieldValidatorMap = new HashMap<String, FieldValidatorImpl>();
    Map<String, FieldValidatorImpl> synonymValidatorMap = new HashMap<String, FieldValidatorImpl>();
    //    Set<FieldGroupValidatorImpl> fieldGroupValidatorSet = new HashSet<FieldGroupValidatorImpl>();
    Set<FieldValidatorImpl> fieldValidatorSet = new HashSet<FieldValidatorImpl>();
    List<FieldValidator> fieldValidatorList = new ArrayList<FieldValidator>();

    static {
        ValidationMessageManager.addBundle("uk.ac.ebi.ena.sra.validation.validationMessages");
    }

    public ChecklistValidatorImpl(String id, List<FieldValidatorImpl> fieldValidatorList) {
        super(id);
        for (FieldValidatorImpl fieldValidatorImpl : fieldValidatorList) {
            fieldValidatorMap.put(fieldValidatorImpl.getName(), fieldValidatorImpl);
        }
    }

    public ChecklistValidatorImpl(ChecklistType checklistType) {
        super(checklistType.getAccession());
        for (ChecklistType.DESCRIPTOR.FIELDGROUP fieldGroup : checklistType.getDESCRIPTOR().getFIELDGROUPArray()) {
            Set<FieldValidatorImpl> fieldValidatorSet = new HashSet<FieldValidatorImpl>();
            for (ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD field : fieldGroup.getFIELDArray()) {
                final FieldValidatorImpl fieldValidator = new FieldValidatorImpl(field);
                fieldValidatorMap.put(getFilterString(fieldValidator.getFieldLabel()), fieldValidator);
                fieldValidatorSet.add(fieldValidator);
                this.fieldValidatorSet.add(fieldValidator);
                fieldValidatorList.add(fieldValidator);

                for (String synonym : fieldValidator.getSynonymArray()) {
                    synonymValidatorMap.put(getFilterString(synonym), fieldValidator);
                }
            }
            FieldGroupValidatorImpl fieldGroupValidator = new FieldGroupValidatorImpl(fieldGroup, fieldValidatorSet);
//            fieldGroupValidatorSet.add(fieldGroupValidator);
            fieldValidatorList.add(fieldGroupValidator);
        }

        // loop through checklist conditions
        for (ChecklistType.DESCRIPTOR.CONDITION condition : checklistType.getDESCRIPTOR().getCONDITIONArray()) {
            final String name = condition.getNAME();
            String conditionExpression = condition.getEXPRESSION();
            String conditionError = condition.getERROR();
            ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, getId(), name, conditionExpression, conditionError);
            fieldValidatorList.add(conditionValidator);
        }
    }

    @Override
    public boolean validate(Attribute attribute, ValidationResult validationResult) throws ValidationException {
        FieldValidatorImpl fieldValidator = null;
        boolean validationStatus = true;
        if ((fieldValidator = fieldValidatorMap.get(getFilterString(attribute.getTagName()))) != null) {
            validationStatus = fieldValidator.validate(attribute, validationResult);
            attribute.setTagName(fieldValidator.getFieldLabel());
        } else if ((fieldValidator = synonymValidatorMap.get(getFilterString(attribute.getTagName()))) != null) {
            validationStatus = fieldValidator.validate(attribute, validationResult);
            attribute.setTagName(fieldValidator.getFieldLabel());
        }
        return validationStatus;
    }

    @Override
    public boolean validate(List<Attribute> attributeList, ValidationResult validationResult) throws ValidationException {
        reset();
        super.validate(attributeList, validationResult);
        validate(validationResult);
        if (validationResult.count(Severity.ERROR) > 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validate(ValidationResult validationResult) throws ValidationException {
        boolean validationStatus = true;
        for (FieldValidator fieldValidator : fieldValidatorList) {
            if (!fieldValidator.validate(validationResult)) {
                validationStatus = false;
            }
        }
        return validationStatus;
    }

    public List<FieldValidator> getChecklistValidatorList() {
        return fieldValidatorList;
    }

    public void reset() {
        for (FieldValidator fieldValidator : fieldValidatorList) {
            fieldValidator.reset();
        }
    }

}
