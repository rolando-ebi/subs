package uk.ac.ebi.subs.validation.checklist;

import org.apache.commons.lang.StringUtils;

import uk.ac.ebi.subs.validation.ValidationMessage;
import uk.ac.ebi.subs.validation.ValidationResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextChoiceFieldValidator extends AbstractAttributeValidator {

    private Map<String, List<String>> textValueSymonymMap = new HashMap<String, List<String>>();

    public TextChoiceFieldValidator(String id, Map<String, List<String>> textValueSymonymMap) {
        super(id);
        this.textValueSymonymMap = textValueSymonymMap;
    }

    public TextChoiceFieldValidator(String id, String[] values) {
        super(id);
        for (String value : values) {
            textValueSymonymMap.put(value, new ArrayList<String>());
        }
    }

    public TextChoiceFieldValidator(String id, List<String> valueList) {
        super(id);
        for (String value : valueList) {
            textValueSymonymMap.put(value, new ArrayList<String>());
        }
    }

    public boolean validate(Attribute attribute, ValidationResult validationResult) {
        boolean validationStatus = false;
        // remove whitespace, '-', '_', '(', ')', ':', ';', '.', ',', '/', '\', '#', single and double quote
        for (String textValue : textValueSymonymMap.keySet()) {
            String filteredTextValue = textValue.replaceAll("[,;\\s_:.#\\\\\\/]", "");
            if (getFilterString(textValue).equals(getFilterString(attribute.getTagValue()))) {
                validationStatus = true;
                attribute.setTagValue(textValue);
                break;
            } else {
                List<String> symonymList = textValueSymonymMap.get(textValue);
                for (String symonym : symonymList) {
                    if (getFilterString(symonym).equals(getFilterString(attribute.getTagValue()))) {
                        validationStatus = true;
                        attribute.setTagValue(textValue);
                        validationResult.append(ValidationMessage.info("ERAM.2.1.12", attribute.getTagName(), symonym, textValue));
                        break;
                    }
                }
            }
        }
        if (validationStatus == false)
            validationResult.append(ValidationMessage.error("ERAM.2.1.11", attribute.getTagValuePosition(), attribute.getTagValue(), attribute.getTagName(), StringUtils.join(textValueSymonymMap.keySet(), ",")));
        return validationStatus;
    }

    public Map<String, List<String>> getTextValueSymonymMap() {
        return textValueSymonymMap;
    }

    public void setTextValueSymonymMap(Map<String, List<String>> textValueSymonymMap) {
        this.textValueSymonymMap = textValueSymonymMap;
    }

    public void addTextValueSymonymList(String textValue, List<String> synonymList) {
        textValueSymonymMap.put(textValue, synonymList);
    }

    public void addTextValueSymonymList(String textValue, String[] synonyms) {
        textValueSymonymMap.put(textValue, Arrays.asList(synonyms));
    }
}
