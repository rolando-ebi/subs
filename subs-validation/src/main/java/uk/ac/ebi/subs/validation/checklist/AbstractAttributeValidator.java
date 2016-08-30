package uk.ac.ebi.subs.validation.checklist;

import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationResult;

import java.util.List;

public abstract class AbstractAttributeValidator implements ChecklistValidator {
    public static final String FILTER_REGEX = "[,;\\s_:.#\\\\\\/]";
    private String id = null;

    public AbstractAttributeValidator(String id) {
        this.id = id;
    }

    public static String getFilterString(String string) {
        if (string != null) {
            return string.toUpperCase().replaceAll(FILTER_REGEX, "");
        } else {
            return null;
        }

    }

    public static boolean equals(String name, String providedName) {
        return (getFilterString(name).equalsIgnoreCase(getFilterString(providedName)));
    }

    @Override
    public boolean validate(List<Attribute> attributeList, ValidationResult validationResult) throws ValidationException {
        boolean validationStatus = true;
        for (Attribute attribute : attributeList) {
            if (!validate(attribute, validationResult)) {
                validationStatus = false;
            }
        }
        return validationStatus;
    }

    public String getId() {
        return id;
    }

    public void reset() {
    }
}
