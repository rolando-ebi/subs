package uk.ac.ebi.subs.validation.checklist;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.ebi.ena.sra.validation.checklist.condition.ChecklistConditionLexer;
import uk.ac.ebi.ena.sra.validation.checklist.condition.ChecklistConditionParser;
import uk.ac.ebi.subs.validation.ValidationException;
import uk.ac.ebi.subs.validation.ValidationMessage;
import uk.ac.ebi.subs.validation.ValidationResult;

import java.util.List;
import java.util.Map;

public class ConditionValidator extends AbstractAttributeValidator implements FieldValidator {
    static Logger logger = LoggerFactory.getLogger(ConditionValidator.class);
    private final ChecklistConditionLexer checklistConditionLexer;
    private final ChecklistConditionParser checklistConditionParser;
    Map<String, FieldValidatorImpl> fieldValidatorMap = null;
    private String expression = null;
    private String error = null;

    public ConditionValidator(Map<String, FieldValidatorImpl> fieldValidatorMap, String id, String name, String expression, String error) {
        super(id);
        this.fieldValidatorMap = fieldValidatorMap;
        this.expression = expression;
        this.error = error;
        final CharStream charStream = new ANTLRInputStream(expression);
        logger.info("Constructing new lexer and parser for expression " + expression);
        checklistConditionLexer = new ChecklistConditionLexer(charStream);
        final CommonTokenStream commonTokenStream = new CommonTokenStream(checklistConditionLexer);
        checklistConditionParser = new ChecklistConditionParser(commonTokenStream);
    }

    @Override
    public boolean validate(List<Attribute> attributeList, ValidationResult validationResult) throws ValidationException {
        //checklistConditionParser.resetFields();
        return super.validate(attributeList, validationResult);
    }

    @Override
    public boolean validate(Attribute attribute, ValidationResult validationResult) throws ValidationException {
        //checklistConditionParser.addField(attribute.getTagName(),attribute.getTagValue());
        return true;
    }

    @Override
    public boolean validate(ValidationResult validationResult) throws ValidationException {

        for (String fieldName : fieldValidatorMap.keySet()) {
            FieldValidatorImpl fieldValidatorImpl = fieldValidatorMap.get(fieldName);
            if (fieldValidatorImpl.isValidated()) {
                for (java.lang.String fieldValue : fieldValidatorImpl.getValues()) {
                    checklistConditionParser.addField(fieldValidatorImpl.getFieldName(), fieldValue);
                }

            }
        }
        final ChecklistConditionParser.ConditionContext condition = checklistConditionParser.condition();
        if (condition.result) {
            return true;
        } else {
            validationResult.append(ValidationMessage.error("ERAM.2.1.13", error));
            logger.info(error);
            return false;
        }

    }

    public String getExpression() {
        return expression;
    }

    public String getError() {
        return error;
    }

    public Map<String, FieldValidatorImpl> getFieldValidatorMap() {
        return fieldValidatorMap;
    }

    public void reset() {
        checklistConditionParser.resetFields();
        checklistConditionParser.reset();
    }
}
