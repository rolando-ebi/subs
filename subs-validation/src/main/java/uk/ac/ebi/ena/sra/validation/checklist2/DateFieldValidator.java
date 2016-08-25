package uk.ac.ebi.ena.sra.validation.checklist2;

import org.joda.time.DateTime;

import uk.ac.ebi.embl.api.validation.ValidationMessage;
import uk.ac.ebi.embl.api.validation.ValidationResult;

import java.text.DateFormat;

public class DateFieldValidator extends AbstractAttributeValidator {
    private DateFormat dateFormat;

    public DateFieldValidator(String id, DateFormat dateFormat) {
        super(id);
        this.dateFormat = dateFormat;
    }

    public DateFieldValidator(String id) {
        super(id);
    }

    public boolean validate(Attribute attribute, ValidationResult validationResult) {
        try {
            DateTime dateTime = new DateTime(attribute.getTagValue());
//            attribute.setTagValue(dateTime.toDateTimeISO().toString());
        } catch (IllegalArgumentException e) {
            validationResult.append(ValidationMessage.error("ERAM.2.1.10", attribute.getTagValuePosition(), attribute.getTagValue(), attribute.getTagName()));
            return false;

        }
        return true;
    }

}
