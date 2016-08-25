package uk.ac.ebi.ena.sra.validation.checklist2;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class AttributeValidatorFactoryImplTest {
    private static final String DEFAULT_CHECKLIST_ID = "ERC000011";


    @Test
    public void testCreateChecklistValidator() throws Exception {
        final ChecklistValidatorFactory checklistValidatorFactory = ChecklistValidatorFactory.newInstance();
        final ChecklistValidator checklistValidator = checklistValidatorFactory.createChecklistValidator(DEFAULT_CHECKLIST_ID);
        assertNotNull(checklistValidator);
    }

}