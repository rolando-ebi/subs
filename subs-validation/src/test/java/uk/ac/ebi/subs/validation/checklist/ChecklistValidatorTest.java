package uk.ac.ebi.subs.validation.checklist;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.subs.validation.ValidationMessageManager;
import uk.ac.ebi.subs.validation.ValidationResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ChecklistValidatorTest {
    public static final String CHECKLIST_ID = "ERC000026";
    ChecklistValidator checklistValidator;
    ValidationResult validationResult = null;

    @Before
    public void setUp() throws Exception {
        final ChecklistValidatorFactory checklistValidatorFactory = ChecklistValidatorFactory.newInstance(ChecklistValidatorFactoryImpl.class);
        checklistValidator = checklistValidatorFactory.createChecklistValidator(CHECKLIST_ID);
        validationResult = new ValidationResult();
        ValidationMessageManager.addBundle("uk.ac.ebi.ena.sra.validation.validationMessages");

    }

    @After
    public void tearDown() throws Exception {

    }

    //    @Test
    public void validate() throws Exception {
        List<Attribute> attributeList = new ArrayList<Attribute>();
    }

    @Test
    public void validateFailedNoAttributes() throws Exception {
        List<Attribute> attributeList = new ArrayList<Attribute>();
        final boolean validate = checklistValidator.validate(attributeList, validationResult);
        assertFalse(validate);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(checklistValidator.getId(), CHECKLIST_ID);

    }

}