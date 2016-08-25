package uk.ac.ebi.ena.sra.validation.checklist2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.embl.api.validation.Severity;
import uk.ac.ebi.embl.api.validation.ValidationMessageManager;
import uk.ac.ebi.embl.api.validation.ValidationResult;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateFieldValidatorTest {
    public static final String ISO_8601_DATE_1 = "2005";
    public static final String ISO_8601_DATE_2 = "2005-05";
    public static final String ISO_8601_DATE_3 = "1997-07-16";
    public static final String ISO_8601_DATE_4 = "1997-07-16";
    public static final String ISO_8601_DATE_5 = "1997-07-16T19:20:30+01:00";
    public static final String ISO_8601_DATE_6 = "1994-11-05T08:15:30-05:00";
    public static final String NON_ISO_8601_DATE_1 = "01-May-02";
    public static final String NON_ISO_8601_DATE_2 = "12-2010";

    ValidationResult validationResult = null;
    ChecklistValidator dateFieldValidator = null;


    @Before
    public void setUp() throws Exception {
        ValidationMessageManager.addBundle("uk.ac.ebi.ena.sra.validation.validationMessages");
        validationResult = new ValidationResult();
        dateFieldValidator = new DateFieldValidator("Test date validator");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidate() throws Exception {
        Attribute attribute = new AttributeImpl("Example Tag", ISO_8601_DATE_1, null);
        dateFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testValidate1() throws Exception {
        Attribute attribute = new AttributeImpl("Example Tag", ISO_8601_DATE_2, null);
        dateFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testValidate2() throws Exception {
        Attribute attribute = new AttributeImpl("Example Tag", ISO_8601_DATE_3, null);
        dateFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testValidate3() throws Exception {
        Attribute attribute = new AttributeImpl("Example Tag", ISO_8601_DATE_4, null);
        dateFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testValidate4() throws Exception {
        Attribute attribute = new AttributeImpl("Example Tag", ISO_8601_DATE_5, null);
        dateFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testValidate5() throws Exception {
        Attribute attribute = new AttributeImpl("Example Tag", ISO_8601_DATE_6, null);
        dateFieldValidator.validate(attribute, validationResult);
        assertTrue(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testValidate6() throws Exception {
        Attribute attribute = new AttributeImpl("Example Tag", NON_ISO_8601_DATE_1, null);
        dateFieldValidator.validate(attribute, validationResult);
        assertFalse(validationResult.getMessages(Severity.ERROR).isEmpty());
    }

    @Test
    public void testValidate7() throws Exception {
        Attribute attribute = new AttributeImpl("Example Tag", NON_ISO_8601_DATE_2, null);
        dateFieldValidator.validate(attribute, validationResult);
        assertFalse(validationResult.getMessages(Severity.ERROR).isEmpty());
    }
}