package uk.ac.ebi.ena.sra.validation.checklist2;

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
