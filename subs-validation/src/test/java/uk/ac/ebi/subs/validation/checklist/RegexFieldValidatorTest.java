package uk.ac.ebi.subs.validation.checklist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.subs.validation.ValidationMessageManager;
import uk.ac.ebi.subs.validation.ValidationResult;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegexFieldValidatorTest {
    ValidationResult validationResult = null;
    RegexFieldValidator regexFieldValidator = null;
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 10;


    @Before
    public void setUp() throws Exception {
        ValidationMessageManager.addBundle("uk.ac.ebi.ena.sra.validation.validationMessages");
        validationResult = new ValidationResult();
        regexFieldValidator = new RegexFieldValidator("Test", MIN_LENGTH, MAX_LENGTH, "[+-]?[0-9]+$");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidateSuccess() {
        Attribute attribute = new AttributeImpl("Example Tag", "123456789", null);
        final boolean validate = regexFieldValidator.validate(attribute, validationResult);
        assertTrue(validate);
    }

    @Test
    public void testValidateFailed() {
        Attribute attribute = new AttributeImpl("Example Tag", "123456789W", null);
        final boolean validate = regexFieldValidator.validate(attribute, validationResult);
        assertFalse(validate);
    }

}