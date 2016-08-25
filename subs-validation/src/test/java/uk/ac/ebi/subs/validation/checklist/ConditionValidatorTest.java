package uk.ac.ebi.subs.validation.checklist;

import org.junit.Before;
import org.junit.Test;

import uk.ac.ebi.ena.sra.xml.ChecklistType;
import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationMessageManager;
import uk.ac.ebi.subs.validation.ValidationResult;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConditionValidatorTest {
    Map<String, FieldValidatorImpl> fieldValidatorMap = null;
    FieldValidatorImpl fieldValidatorImpl1 = null;
    FieldValidatorImpl fieldValidatorImpl2 = null;
    FieldValidatorImpl fieldValidatorImpl3 = null;
    ValidationResult validationResult = new ValidationResult();

    @Before
    public void setup() {
        ValidationMessageManager.addBundle("uk.ac.ebi.ena.sra.validation.validationMessages");
        TextAreaFieldValidator textAreaFieldValidator1 = new TextAreaFieldValidator("Text_Area_Validator_1");
        TextAreaFieldValidator textAreaFieldValidator2 = new TextAreaFieldValidator("Text_Area_Validator_2");
        TextChoiceFieldValidator textChoiceFieldValidator = new TextChoiceFieldValidator("Colour Validator", new String[]{"Red", "Blue", "Green"});
        fieldValidatorImpl1 = new FieldValidatorImpl("Field_A", "Field_A", "Field_A", textAreaFieldValidator1, new HashSet<String>(),
                ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MANDATORY.RECOMMENDED, ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MULTIPLICITY.MULTIPLE);
        fieldValidatorImpl2 = new FieldValidatorImpl("Field_B", "Field_B", "Field_B", textAreaFieldValidator2, new HashSet<String>(),
                ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MANDATORY.RECOMMENDED, ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MULTIPLICITY.MULTIPLE);
        fieldValidatorImpl3 = new FieldValidatorImpl("Field_C", "Field_C", "Field_C", textChoiceFieldValidator, new HashSet<String>(),
                ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MANDATORY.RECOMMENDED, ChecklistType.DESCRIPTOR.FIELDGROUP.FIELD.MULTIPLICITY.MULTIPLE);
        fieldValidatorMap = new HashMap<String, FieldValidatorImpl>();
        fieldValidatorMap.put(fieldValidatorImpl1.getFieldName(), fieldValidatorImpl1);
        fieldValidatorMap.put(fieldValidatorImpl2.getFieldName(), fieldValidatorImpl2);
        fieldValidatorMap.put(fieldValidatorImpl3.getFieldName(), fieldValidatorImpl3);
    }

    @Test
    public void testCondition1() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "(Field_A and Field_B) and Field_C = \"Blue\"", "Field_A and Field_B and Field_C must equal Blue");
        Attribute attribute1 = new AttributeImpl("Field_A", "5", "m");
        fieldValidatorImpl1.validate(attribute1, validationResult);
        Attribute attribute2 = new AttributeImpl("Field_B", "5", "m");
        fieldValidatorImpl2.validate(attribute2, validationResult);
        Attribute attribute3 = new AttributeImpl("Field_C", "Blue", null);
        fieldValidatorImpl3.validate(attribute3, validationResult);
        assertTrue(conditionValidator.validate(validationResult));
    }

    @Test
    public void testOrCondition1() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A or Field_B", "Either Field_A or Field_B must be provided");
        Attribute attribute = new AttributeImpl("Field_A", "5", "m");
        fieldValidatorImpl1.validate(attribute, validationResult);
        assertTrue(conditionValidator.validate(validationResult));
    }

    @Test
    public void testOrCondition2() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A or Field_B", "Either Field_A or Field_B must be provided");
        Attribute attribute = new AttributeImpl("Field_B", "5", "m");
        fieldValidatorImpl1.validate(attribute, validationResult);
        assertTrue(conditionValidator.validate(validationResult));
    }

    @Test
    public void testOrCondition3() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A or Field_B", "Either Field_A or Field_B must be provided");
        Attribute attribute1 = new AttributeImpl("Field_A", "5", "m");
        fieldValidatorImpl1.validate(attribute1, validationResult);
        Attribute attribute2 = new AttributeImpl("Field_B", "5", "m");
        fieldValidatorImpl1.validate(attribute2, validationResult);
        assertTrue(conditionValidator.validate(validationResult));
    }

    @Test
    public void testFailedOrCondition2() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A or Field_B", "Either Field_A or Field_B must be provided");
        Attribute attribute1 = new AttributeImpl("Field_A", "", "m");
        fieldValidatorImpl1.validate(attribute1, validationResult);
        Attribute attribute2 = new AttributeImpl("Field_B", "", "m");
        fieldValidatorImpl1.validate(attribute2, validationResult);
        assertFalse(conditionValidator.validate(validationResult));
    }

    @Test
    public void testFailedOrCondition() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A or Field_B", "Either Field_A or Field_B must be provided");
        boolean result = conditionValidator.validate(validationResult);
        assertFalse(result);
    }

    @Test
    public void testFailedAndCondition1() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A and Field_B", "Both Field_A and Field_B must be provided");
        assertFalse(conditionValidator.validate(validationResult));
    }

    @Test
    public void testFailedAndCondition2() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A and Field_B", "Either Field_A and Field_B must be provided");
        Attribute attribute = new AttributeImpl("Field_A", "5", "m");
        fieldValidatorImpl1.validate(attribute, validationResult);
        assertFalse(conditionValidator.validate(validationResult));
    }

    @Test
    public void testFailedAndCondition3() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A and Field_B", "Field_A and Field_B must be provided");
        Attribute attribute = new AttributeImpl("Field_B", "5", "m");
        fieldValidatorImpl1.validate(attribute, validationResult);
        assertFalse(conditionValidator.validate(validationResult));
    }

    @Test
    public void testFailedAndCondition4() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A and Field_B", "Field_A and Field_B must be provided");
        Attribute attribute1 = new AttributeImpl("Field_A", "#", "m");
        fieldValidatorImpl1.validate(attribute1, validationResult);
        Attribute attribute2 = new AttributeImpl("Field_B", "34", "m");
        fieldValidatorImpl2.validate(attribute2, validationResult);
        assertFalse(conditionValidator.validate(validationResult));
    }

    @Test
    public void testFailedAndCondition5() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A and Field_B", "Field_A and Field_B must be provided");
        Attribute attribute1 = new AttributeImpl("Field_A", "", "m");
        fieldValidatorImpl1.validate(attribute1, validationResult);
        Attribute attribute2 = new AttributeImpl("Field_B", "34", "m");
        fieldValidatorImpl2.validate(attribute2, validationResult);
        assertFalse(conditionValidator.validate(validationResult));
    }

    @Test
    public void testAndCondition() throws ValidationException {
        ConditionValidator conditionValidator = new ConditionValidator(fieldValidatorMap, "Condition_1", "Condition_1", "Field_A and Field_B", "Field_A and Field_B must be provided");
        Attribute attribute1 = new AttributeImpl("Field_A", "5", "m");
        fieldValidatorImpl1.validate(attribute1, validationResult);
        Attribute attribute2 = new AttributeImpl("Field_B", "5", "m");
        fieldValidatorImpl2.validate(attribute2, validationResult);
        assertTrue(conditionValidator.validate(validationResult));
    }

}
