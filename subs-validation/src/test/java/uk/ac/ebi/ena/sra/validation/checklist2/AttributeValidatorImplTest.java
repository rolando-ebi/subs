package uk.ac.ebi.ena.sra.validation.checklist2;

import org.apache.xmlbeans.XmlString;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.ena.sra.xml.CHECKLISTSETDocument;
import uk.ac.ebi.ena.sra.xml.ChecklistType;

public class AttributeValidatorImplTest {
    public static final String TEST_UNIT = "millibar(hPa)";
    ChecklistType checklistType = null;

    @Before
    public void setUp() {
        final CHECKLISTSETDocument checklistsetDocument = CHECKLISTSETDocument.Factory.newInstance();
        checklistType = checklistsetDocument.addNewCHECKLISTSET().addNewCHECKLIST();
    }

    //@Test
    public void testValidate() throws Exception {

    }

    @Test
    public void testValidateFieldGroup() throws Exception {
        final ChecklistType.DESCRIPTOR.FIELDGROUP fieldGroup = checklistType.addNewDESCRIPTOR().addNewFIELDGROUP();
        fieldGroup.setRestrictionType(ChecklistType.DESCRIPTOR.FIELDGROUP.RestrictionType.ANY_NUMBER_OR_NONE_OF_THE_FIELDS);

    }

    @Test
    public void testValidateField() throws Exception {
        final ChecklistType.DESCRIPTOR.FIELDGROUP fieldGroup = checklistType.addNewDESCRIPTOR().addNewFIELDGROUP();
        final ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD field = fieldGroup.addNewFIELD();
        final ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.UNITS units = field.addNewUNITS();
        final XmlString xmlString = units.addNewUNIT();
    }

    //@Test
    public void testValidateTextFieldRegExValue() throws Exception {

    }

    //@Test
    public void testValidateTextChoiceFieldTextValue() throws Exception {

    }

    //@Test
    public void testValidateTextChoiceFieldOntology() throws Exception {

    }

    //@Test
    public void testValidateDateField() throws Exception {

    }

    //@Test
    public void testValidateTaxonField() throws Exception {

    }

    //@Test
    public void testValidateMandatory() throws Exception {

    }

    //@Test
    public void testValidateMultiplicity() throws Exception {

    }
}