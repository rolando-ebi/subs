package uk.ac.ebi.subs.validation.checklist;

public class AttributeImpl implements Attribute {
    private String tagName;
    private String tagValue;
    private String units;
    private String sampleId;


    public AttributeImpl(String tagName, String tagValue, String units) {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.units = units;
    }

    public AttributeImpl(String tagName, String tagValue) {
        this.tagName = tagName;
        this.tagValue = tagValue;
    }

    public AttributeImpl(String tagName, String tagValue, String units, String sampleId) {
        this.tagName = tagName;
        this.tagValue = tagValue;
        this.units = units;
        this.sampleId = sampleId;
    }

    public AttributeImpl(String tagName) {
        this.tagName = tagName;
    }


    @Override
    public String getTagName() {
        return tagName;
    }

    @Override
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String getTagValue() {
        return tagValue;
    }

    @Override
    public void setTagValue(String tagValue) {
        this.tagValue = tagValue;
    }

    @Override
    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String toString() {
        return "sample id " + sampleId;
    }

    @Override
    public String getTagNamePosition() {
        return toString();
    }

    @Override
    public String getTagValuePosition() {
        return toString();
    }

    @Override
    public String getTagUnitsPosition() {
        return toString();
    }
}
