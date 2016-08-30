package uk.ac.ebi.subs.validation.checklist;

import org.apache.xmlbeans.XmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import uk.ac.ebi.ena.sra.xml.CHECKLISTSETDocument;
import uk.ac.ebi.ena.sra.xml.ChecklistType;
import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

public class AttributeValidatorImpl extends AbstractAttributeValidator {
    static Logger logger = LoggerFactory.getLogger(AttributeValidatorImpl.class);
    public List<ValidationCheck> validationCheckList = new ArrayList<ValidationCheck>();

    /*
    FIELD_GROUP/@restrictiontype ('Any...', 'One...', 'At least...','One or none') Check that the list of fiends in the field group match restructiontype. 'Any...' implies no restriction and is the default if the attribute is missing. The other are self-explanatory.
    FIELD/units: ID defined check that only one of the allowed units is used.
    TEXT_FIELD and TEXT_AREA_FIELD /MIN_LENGTH/MAX_LENGTH ( if defined)
    TEXT_FIELD/REGEX_VALUE ( if defined) as now i.e. check that value matches regexp
    TEXT_FIELD/REGEX_GROUP (if defined): check that for the given regexp capture group the value matches one given in REGEX_GROUP/TEXT_VALUE
    TEXT_CHOICE_FIELD/TEXT_VALUE: as now i.e. check that value is allowed. Match case insensitively and fix casing if matched.
    TEXT_CHOICE_FIELD/ONTOLOGY: do not implement yet
    DATE_FIELD: allow XML date format. Allow oracle format masks ( convert to XML data format ). Not urgent.
    BOOLEAN FIELD: check that no value is defined. Not urgent.
    TAXON_FIELD: most complex check. Permitted and non permitted sub-taxa trees. Will require Iain's taxonomy search service support. To be discussed and planned.
    MANDATORY: field must exist
    MULTIPLICITY: single or multiple occurrence allowed
    CONDITION: condition parser provided
    */
    protected ChecklistType checklistType = null;
    Document checklistDocument = null;

    public AttributeValidatorImpl(Document checklistDocument) throws XmlException {
        this(CHECKLISTSETDocument.Factory.parse(checklistDocument));
    }

    public AttributeValidatorImpl(String documentString) throws XmlException {
        this(CHECKLISTSETDocument.Factory.parse(documentString));
    }

    public AttributeValidatorImpl(ChecklistType checklistType) {
        super(checklistType.getAccession());
        this.checklistType = checklistType;
    }

    public AttributeValidatorImpl(CHECKLISTSETDocument ChecklistSetDocument) throws XmlException {
        super("checklist");
        if (ChecklistSetDocument.getCHECKLISTSET() != null) {
            final ChecklistType[] checklistArray = ChecklistSetDocument.getCHECKLISTSET().getCHECKLISTArray();
            if (checklistArray != null && checklistArray.length > 0) {
                this.checklistType = checklistArray[0];
            } else {
                throw new XmlException("No checklist element found in checklist_set");
            }
        } else {
            throw new XmlException("No checklist_set found");
        }
        final ChecklistType.DESCRIPTOR.FIELDGROUP[] fieldgroupArray = checklistType.getDESCRIPTOR().getFIELDGROUPArray();
        if (fieldgroupArray != null) {
            for (ChecklistType.DESCRIPTOR.FIELDGROUP fieldGroup : fieldgroupArray) {
                final ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD[] fieldArray = fieldGroup.getFIELDArray();
                if (fieldArray != null) {
                    for (ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD field : fieldArray) {
                        final ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE fieldtype = field.getFIELDTYPE();
                    }
                }
            }
        }
    }

    @Override
    public boolean validate(Attribute attribute, ValidationResult validationResult) throws ValidationException {
        return false;
    }

    /*
    <xs:enumeration value="Any number or none of the fields"/>
    <xs:enumeration value="One of the fields"/>
    <xs:enumeration value="At least one of the fields"/>
    <xs:enumeration value="One or none of the fields"/>
     */

}
