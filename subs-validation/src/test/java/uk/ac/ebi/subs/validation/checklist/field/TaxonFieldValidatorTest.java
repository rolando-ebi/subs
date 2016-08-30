package uk.ac.ebi.subs.validation.checklist.field;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.subs.validation.Severity;
import uk.ac.ebi.subs.validation.ValidationMessageManager;
import uk.ac.ebi.subs.validation.ValidationResult;
import uk.ac.ebi.subs.validation.checklist.Attribute;
import uk.ac.ebi.subs.validation.checklist.AttributeImpl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TaxonFieldValidatorTest {
    public String[] TAXON_IDS = {"9606", "Mus musculus"};
    private TaxonFieldValidator taxonFieldValidator = null;
    private ValidationResult validationResult;


    @Before
    public void setUp() throws Exception {
        ValidationMessageManager.addBundle("uk.ac.ebi.ena.sra.validation.validationMessages");
        validationResult = new ValidationResult();
//        taxonFieldValidator = new TaxonFieldValidator("Test", ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.FIELDTYPE.TAXONFIELD.RestrictionType.PERMITTED_TAXA,TAXON_IDS);
        taxonFieldValidator = new TaxonFieldValidator("Test");
    }

    @After
    public void finish() throws Exception {

    }

    @Test
    public void testValidateTaxId() throws Exception {
        assertNotNull(taxonFieldValidator);
        Attribute attribute = new AttributeImpl("Example Tag", "9606", null);
        taxonFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testValidateScientificName() throws Exception {
        assertNotNull(taxonFieldValidator);
        Attribute attribute = new AttributeImpl("Example Tag", "Homo sapiens", null);
        taxonFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testCommonName() throws Exception {
        assertNotNull(taxonFieldValidator);
        Attribute attribute = new AttributeImpl("Example Tag", "Mouse", null);
        taxonFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testFailValidateTaxId() throws Exception {
        assertNotNull(taxonFieldValidator);
        Attribute attribute = new AttributeImpl("Example Tag", "1234456667564", null);
        taxonFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).size() == 1);
    }

    @Test
    public void testFailValidateScientificName() throws Exception {
        assertNotNull(taxonFieldValidator);
        Attribute attribute = new AttributeImpl("Example Tag", "Homo sapins", null);
        taxonFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).size() == 1);
    }

    @Test
    public void testFailCommonName() throws Exception {
        assertNotNull(taxonFieldValidator);
        Attribute attribute = new AttributeImpl("Example Tag", "Moue", null);
        taxonFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).size() == 1);
    }

}