package uk.ac.ebi.subs.validation.checklist;

import org.joda.time.DateTime;

import uk.ac.ebi.subs.validation.ValidationMessage;
import uk.ac.ebi.subs.validation.ValidationResult;

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
