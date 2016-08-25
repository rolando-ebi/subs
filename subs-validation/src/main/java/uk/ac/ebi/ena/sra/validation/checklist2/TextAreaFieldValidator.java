package uk.ac.ebi.ena.sra.validation.checklist2;

import uk.ac.ebi.embl.api.validation.ValidationMessage;
import uk.ac.ebi.embl.api.validation.ValidationResult;

public class TextAreaFieldValidator extends AbstractAttributeValidator {
    private Integer minLength = null;
    private Integer maxLength = null;

    public TextAreaFieldValidator(String id, Integer minLength, Integer maxLength) {
        super(id);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    public TextAreaFieldValidator(String id) {
        super(id);
    }

    public boolean validate(Attribute attribute, ValidationResult validationResult) {
        if (attribute.getTagValue() != null) {
            if (minLength != null && minLength > 0 && attribute.getTagValue().length() < minLength) {
                validationResult.append(ValidationMessage.error("ERAM.2.1.2", attribute.getTagValuePosition(), attribute.getTagName(), attribute.getTagValue(), minLength));
                return false;
            } else if (maxLength != null && maxLength > 0 && attribute.getTagValue().length() > maxLength) {
                validationResult.append(ValidationMessage.error("ERAM.2.1.3", attribute.getTagValuePosition(), attribute.getTagName(), attribute.getTagValue(), maxLength));
            }
        }
        return true;
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
}
