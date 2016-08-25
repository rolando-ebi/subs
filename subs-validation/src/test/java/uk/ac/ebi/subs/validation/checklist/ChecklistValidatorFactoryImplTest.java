package uk.ac.ebi.subs.validation.checklist;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ebi.subs.validation.ValidationMessageManager;
import uk.ac.ebi.subs.validation.ValidationResult;

import static org.junit.Assert.assertNotNull;

public class ChecklistValidatorFactoryImplTest {
    ChecklistValidatorFactory checklistValidatorFactoryImpl;
    private ValidationResult validationResult = null;
    static final Logger logger = LoggerFactory.getLogger(ChecklistValidatorFactoryImplTest.class);


    @Before
    public void setup() throws Exception {
        ValidationMessageManager.addBundle("uk.ac.ebi.ena.sra.validation.validationMessages");
        checklistValidatorFactoryImpl = ChecklistValidatorFactory.newInstance(ChecklistValidatorFactoryImpl.class);
//        checklistValidatorFactoryImpl = ChecklistValidatorFactoryImpl.createChecklistValidatorFactory(sqlSession);
        validationResult = new ValidationResult();
    }

    @Test
    public void testCreateChecklistValidator() throws Exception {
        assertNotNull(checklistValidatorFactoryImpl);
        final ChecklistValidator checklistValidator = checklistValidatorFactoryImpl.createChecklistValidator("ERC000015");
        assertNotNull(checklistValidator);
    }

    //    @Test
    public void testValidator() throws Exception {
        validationResult = new ValidationResult();
        final ChecklistValidator checklistValidator = checklistValidatorFactoryImpl.createChecklistValidator("ERC000022");
    }

    //    @Test
    public void testValidatorForSample() throws Exception {
        validationResult = new ValidationResult();

        final ChecklistValidator checklistValidator = checklistValidatorFactoryImpl.createChecklistValidator("ERC000030");
    }
}
