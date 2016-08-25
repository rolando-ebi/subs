package uk.ac.ebi.ena.sra.validation.checklist2;

import org.apache.commons.lang.StringUtils;

import uk.ac.ebi.embl.api.validation.ValidationException;
import uk.ac.ebi.embl.api.validation.ValidationMessage;
import uk.ac.ebi.embl.api.validation.ValidationResult;
import uk.ac.ebi.ena.sra.validation.checklist2.field.TaxonFieldValidator;
import uk.ac.ebi.ena.sra.xml.ChecklistType;
import uk.ac.ebi.ena.sra.xml.ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TEXTCHOICEFIELD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dispatches validation off to the appropriate validator
 */
public class FieldValidatorImpl extends AbstractAttributeValidator implements FieldValidator {
    ChecklistValidator validator = null;
    Boolean mutiplicity = null;
    Set<String> unitSet = new HashSet<String>();
    String[] synonymArray = new String[0];
    private String fieldName = null;
    private String fieldLabel = null;
    private ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MANDATORY.Enum mandatory;
    private ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MULTIPLICITY.Enum multiplicity;
    private List<String> values = new ArrayList<String>();
    private List<String> nonEmptyValues = new ArrayList<String>();

    public FieldValidatorImpl(String id,
                              String fieldName,
                              String fieldLabel,
                              ChecklistValidator validator,
                              Set<String> unitSet,
                              ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MANDATORY.Enum mandatory,
                              ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MULTIPLICITY.Enum multiplicity) {
        super(id);
        this.fieldName = fieldName;
        this.fieldLabel = fieldLabel;
        this.validator = validator;
        this.unitSet = unitSet;
        this.mandatory = mandatory;
        this.multiplicity = multiplicity;
    }

    public FieldValidatorImpl(ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD field) {
        super(field.getNAME().toUpperCase());
        this.fieldName = field.getNAME();
        this.synonymArray = field.getSYNONYMArray();
        this.fieldLabel = field.getLABEL();
        this.mandatory = field.getMANDATORY();
        this.multiplicity = field.getMULTIPLICITY();
        final ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.UNITS units = field.getUNITS();
        if (units != null) {
            for (String unit : units.getUNITArray()) {
                unitSet.add(unit);
            }
        }

        if (field.getFIELDTYPE().isSetDATEFIELD()) {
            validator = new DateFieldValidator(getId());
        } else if (field.getFIELDTYPE().isSetTAXONFIELD()) {
            final ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TAXONFIELD taxonfield = field.getFIELDTYPE().getTAXONFIELD();
            validator = new TaxonFieldValidator(
                    getId(),
                    taxonfield.getRestrictionType(),
                    taxonfield.getTAXONArray()
            );
        } else if (field.getFIELDTYPE().isSetTEXTAREAFIELD()) {
            final ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TEXTAREAFIELD textareafield = field.getFIELDTYPE().getTEXTAREAFIELD();
            Integer minLength = null;
            if (textareafield.getMINLENGTH() != null)
                minLength = textareafield.getMINLENGTH().intValue();
            Integer maxLength = null;
            if (textareafield.getMAXLENGTH() != null)
                maxLength = textareafield.getMAXLENGTH().intValue();
            validator = new TextAreaFieldValidator(
                    getId(),
                    minLength,
                    maxLength
            );
        } else if (field.getFIELDTYPE().isSetTEXTCHOICEFIELD()) {
            Map<String, List<String>> textValueSymonymMap = new HashMap<String, List<String>>();
            final TEXTCHOICEFIELD textChoiceField = field.getFIELDTYPE().getTEXTCHOICEFIELD();
            for (TEXTCHOICEFIELD.TEXTVALUE textValue : textChoiceField.getTEXTVALUEArray()) {
                textValueSymonymMap.put(textValue.getVALUE(), Arrays.asList(textValue.getSYNONYMArray()));
            }
            validator = new TextChoiceFieldValidator(getId(), textValueSymonymMap);
        } else if (field.getFIELDTYPE().isSetTEXTFIELD()) {
            final ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TEXTFIELD textfield = field.getFIELDTYPE().getTEXTFIELD();
            Integer minLength = null;
            if (textfield.getMINLENGTH() != null) minLength = textfield.getMINLENGTH().intValue();
            Integer maxLength = null;
            if (textfield.getMAXLENGTH() != null) maxLength = textfield.getMAXLENGTH().intValue();
            validator = new RegexFieldValidator(
                    getId(),
                    minLength,
                    maxLength,
                    textfield.getREGEXVALUE(),
                    false
            );
        }
        if (validator == null) {
            // error message
        }
    }

    public boolean validate(Attribute attribute, ValidationResult validationResult) throws ValidationException {
        values.add(attribute.getTagValue());
        boolean validationStatus = true;
        // validate units
        if (!unitSet.isEmpty()) {
            if (!unitSet.contains(attribute.getUnits())) {
                validationResult.append(ValidationMessage.error("ERAM.2.1.1", attribute.getTagUnitsPosition(), attribute.getUnits(), attribute.getTagValue(), StringUtils.join(unitSet, ",")));
                validationStatus = false;
            }
        }

        if (validator.validate(attribute, validationResult) && validationStatus) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean getMutiplicity() {
        return mutiplicity;
    }

    public void setMutiplicity(Boolean mutiplicity) {
        this.mutiplicity = mutiplicity;
    }

    public String getName() {
        return fieldName.toUpperCase();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldLabel() {
        return fieldLabel;
    }

    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    public int getValidationCount() {
        return values.size();
    }

    public String[] getSynonymArray() {
        return synonymArray;
    }

    public void setSynonymArray(String[] synonymArray) {
        this.synonymArray = synonymArray;
    }

    public boolean isValidated() {
        /*if (validationCount == 0 )
            return false;
        else
            return true;
            */
        return values.size() > 0;
    }

    public boolean validate(ValidationResult validationResult) throws ValidationException {
        boolean validationStatus = true;
        int nonEmptyValues = 0;
        for (String value : values) {
            if (value != null) {
                if (getFilterString(value).length() > 0) {
                    nonEmptyValues++;
                }
            }
        }

        if (mandatory.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MANDATORY.MANDATORY) && nonEmptyValues == 0) {
            validationResult.append(ValidationMessage.error("ERAM.2.1.7", fieldName));
            validationStatus = false;
        } else if (mandatory.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MANDATORY.OPTIONAL) && values.size() == 0) {
            // do nothing
        } else if (mandatory.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MANDATORY.RECOMMENDED) && values.size() == 0) {
            validationResult.append(ValidationMessage.info("ERAM.2.1.8", fieldName));
        }

        if (multiplicity.equals(ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MULTIPLICITY.SINGLE) && values.size() > 1) {
            validationResult.append(ValidationMessage.error("ERAM.2.1.9", fieldName));
            validationStatus = false;
        }
        return validationStatus;

    }

    public String getId() {
        return fieldName.toUpperCase();
    }

    @Override
    public String toString() {
        return getFilterString(fieldLabel);
    }

    public List<String> getValues() {
        return values;
    }

    public void reset() {
        values.clear();
    }
}
