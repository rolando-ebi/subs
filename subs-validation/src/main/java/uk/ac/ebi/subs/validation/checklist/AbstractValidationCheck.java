package uk.ac.ebi.subs.validation.checklist;

import org.apache.xmlbeans.XmlException;
import org.w3c.dom.Document;

import uk.ac.ebi.ena.sra.xml.CHECKLISTSETDocument;
import uk.ac.ebi.ena.sra.xml.ChecklistType;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public abstract class AbstractValidationCheck implements ValidationCheck {
    public static final String FIELD_GROUP_XPATH = "/CHECKLIST_SET/CHECKLIST[1]/DESCRIPTOR[1]/FIELD_GROUP";
    public static final String RESTRICTION_TYPE_ATTRIBUTE = "restrictionType";
    public static final String FIELD_XPATH = "/CHECKLIST_SET/CHECKLIST[1]/DESCRIPTOR[1]/FIELD_GROUP/FIELD";
    public static final String FIELD = "FIELD";
    public static final String FIELD_GROUP_NAME = "NAME[1]";

    protected XPathFactory xPathFactory = XPathFactory.newInstance();
    protected XPath xpath = xPathFactory.newXPath();

    protected Document checklistDocument = null;
    protected ChecklistType checklistType;

    public AbstractValidationCheck(Document checklistDocument) throws XPathExpressionException, TransformerException, XmlException {
        this.checklistDocument = checklistDocument;
        final CHECKLISTSETDocument checklistsetDocument = CHECKLISTSETDocument.Factory.parse(checklistDocument);
        this.checklistType = checklistsetDocument.getCHECKLISTSET().getCHECKLISTArray(0);
    }

    public Document getChecklistDocument() {
        return checklistDocument;
    }

    public void setChecklistDocument(Document checklistDocument) {
        this.checklistDocument = checklistDocument;
    }

    abstract void initialiseValidator() throws XPathExpressionException;

}
