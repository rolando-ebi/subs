package uk.ac.ebi.ena.sra.validation.checklist2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.embl.api.validation.Severity;
import uk.ac.ebi.embl.api.validation.ValidationResult;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TextAreaFieldValidatorTest {
    ValidationResult validationResult = null;
    TextAreaFieldValidator textAreaFieldValidator = null;
    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 10;

    @Before
    public void setUp() throws Exception {
        validationResult = new ValidationResult();
        textAreaFieldValidator = new TextAreaFieldValidator("Test", MIN_LENGTH, MAX_LENGTH);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testValidateMinLength() throws Exception {
        for (int i = 1; i < MIN_LENGTH; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int j = 1; j <= i; j++) {
                stringBuffer.append(".");
            }
            Attribute attribute = new AttributeImpl("Example Tag", stringBuffer.toString(), null);
            final boolean validate = textAreaFieldValidator.validate(attribute, validationResult);
            assertFalse(validate);
        }

        int errorMessages = validationResult.count(Severity.ERROR);
        assert (errorMessages == MIN_LENGTH - 1);
    }

    @Test
    public void testValidate() throws Exception {
        for (int i = MIN_LENGTH; i <= MAX_LENGTH; i++) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int j = 1; j <= i; j++) {
                stringBuffer.append(".");
            }
            Attribute attribute = new AttributeImpl("Example Tag", stringBuffer.toString(), null);
            final boolean validate = textAreaFieldValidator.validate(attribute, validationResult);
            assertTrue(validate);
        }

        int errorMessages = validationResult.count(Severity.ERROR);
        assert (errorMessages == 0);
    }

    public void testValidateMaxLength() throws Exception {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 1 + 1; i < MAX_LENGTH; i++) {
            stringBuffer.append(".");
        }
        Attribute attribute = new AttributeImpl("Example Tag", stringBuffer.toString(), null);
        final boolean validate = textAreaFieldValidator.validate(attribute, validationResult);
        assertFalse(validate);
        int errorMessages = validationResult.count(Severity.ERROR);
        assert (errorMessages == 1);

    }
}