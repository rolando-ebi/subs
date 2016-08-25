package uk.ac.ebi.ena.sra.validation.checklist2;

import java.util.Set;

public interface ValidationData {
    public Set<String> getAttributeTags();

    public String getAtrributeValue(String tagName);

    public void setAttributeValue(String tagName, String tagValue);

    public String getUnit(String tagName);
}
