package uk.ac.ebi.subs.validation.checklist;

import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlLineNumber;

import uk.ac.ebi.ena.sra.xml.AttributeType;

public class AttributeTypeImpl implements Attribute {

    public AttributeType attributeType = null;
    private XmlLineNumber sampleAttributeElementLineNumber = null;
    private XmlLineNumber tagElementLineNumber = null;
    private XmlLineNumber valueElementLineNumber = null;
    private XmlLineNumber unitsElementLineNumber = null;

    public AttributeTypeImpl(AttributeType attributeType) {
        this.attributeType = attributeType;
        final XmlCursor xmlCursor = attributeType.newCursor();
        sampleAttributeElementLineNumber = (XmlLineNumber) xmlCursor.getBookmark(XmlLineNumber.class);
        xmlCursor.push();
        xmlCursor.toChild("TAG");
        tagElementLineNumber = (XmlLineNumber) xmlCursor.getBookmark(XmlLineNumber.class);
        xmlCursor.pop();
        xmlCursor.push();
        xmlCursor.toChild("VALUE");
        valueElementLineNumber = (XmlLineNumber) xmlCursor.getBookmark(XmlLineNumber.class);
        xmlCursor.pop();
        if (xmlCursor.toChild("UNITS")) {
            unitsElementLineNumber = (XmlLineNumber) xmlCursor.getBookmark(XmlLineNumber.class);
        }
        xmlCursor.dispose();
    }

    @Override
    public String getTagName() {
        return attributeType.getTAG();
    }

    @Override
    public void setTagName(String tagName) {
        attributeType.setTAG(tagName);
    }

    @Override
    public String getTagValue() {
        return attributeType.getVALUE();
    }

    @Override
    public void setTagValue(String tagValue) {
        attributeType.setVALUE(tagValue);
    }

    @Override
    public String getUnits() {
        return attributeType.getUNITS();
    }

    public AttributeType getAttributeType() {
        return attributeType;
    }

    public void setAttributeType(AttributeType attributeType) {
        this.attributeType = attributeType;
    }

    public String toString() {
        if (sampleAttributeElementLineNumber != null) {
            return "line : " + sampleAttributeElementLineNumber.getLine() + " , column " + sampleAttributeElementLineNumber.getColumn();
        } else {
            return null;
        }
    }

    public XmlLineNumber getSampleAttributeElementLineNumber() {
        return sampleAttributeElementLineNumber;
    }

    public XmlLineNumber getTagElementLineNumber() {
        return tagElementLineNumber;
    }

    public XmlLineNumber getValueElementLineNumber() {
        return valueElementLineNumber;
    }

    public XmlLineNumber getUnitsElementLineNumber() {
        return unitsElementLineNumber;
    }

    @Override
    public String getTagNamePosition() {
        String tagNamePosition = "";
        if (tagElementLineNumber != null) {
            tagNamePosition = "line : " + tagElementLineNumber.getLine() + " , column " + tagElementLineNumber.getColumn();
        }
        return tagNamePosition;
    }

    @Override
    public String getTagValuePosition() {
        String tagValuePosition = "";
        if (valueElementLineNumber != null) {
            tagValuePosition = "line : " + valueElementLineNumber.getLine() + " , column " + valueElementLineNumber.getColumn();
        }
        return tagValuePosition;
    }

    @Override
    public String getTagUnitsPosition() {
        String tagUnitsPosition = "";
        if (unitsElementLineNumber != null) {
            tagUnitsPosition = "line : " + unitsElementLineNumber.getLine() + " , column " + unitsElementLineNumber.getColumn();
        }
        return tagUnitsPosition;
    }


}
