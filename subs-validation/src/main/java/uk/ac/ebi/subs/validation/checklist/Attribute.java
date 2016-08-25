package uk.ac.ebi.subs.validation.checklist;

public interface Attribute {
    String getTagName();

    void setTagName(String tagName);

    String getTagValue();

    void setTagValue(String tagValue);

    String getUnits();

    String getTagNamePosition();

    String getTagValuePosition();

    String getTagUnitsPosition();

}
