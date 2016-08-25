package uk.ac.ebi.ena.sra.validation.checklist2;

import uk.ac.ebi.embl.api.validation.ValidationMessage;
import uk.ac.ebi.embl.api.validation.ValidationResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFieldValidator extends TextAreaFieldValidator {
    private Pattern pattern;

    public RegexFieldValidator(String id, Integer minLength, Integer maxLength, String regexPatternString, boolean quote) {
        super(id, minLength, maxLength);
        if (regexPatternString != null) {
            if (quote) {
                this.pattern = Pattern.compile(Pattern.quote(regexPatternString));
            } else {
                this.pattern = Pattern.compile(regexPatternString);
            }
        }
    }

    public RegexFieldValidator(String id, Integer minLength, Integer maxLength, String regexPatternString) {
        this(id, minLength, maxLength, regexPatternString, false);
    }

    public boolean validate(Attribute attribute, ValidationResult validationResult) {
        boolean validationStatus = super.validate(attribute, validationResult);
        boolean regexValidationStatus = false;
        if (pattern != null) {
            final Matcher matcher = pattern.matcher(attribute.getTagValue());
            if (!matcher.find()) {
                validationResult.append(ValidationMessage.error("ERAM.2.1.4", attribute.getTagValuePosition(), attribute.getTagValue(), attribute.getTagName(), pattern));
                regexValidationStatus = false;
            } else {
                regexValidationStatus = true;
            }
        }
        return (validationStatus && regexValidationStatus);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
}
