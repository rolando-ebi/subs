package uk.ac.ebi.subs.validation.checklist.field;

import uk.ac.ebi.ena.sra.xml.CHECKLISTSETDocument;
import uk.ac.ebi.ena.sra.xml.ChecklistType;
import uk.ac.ebi.subs.validation.ValidationResult;
import uk.ac.ebi.subs.validation.checklist.Attribute;
import uk.ac.ebi.subs.validation.checklist.ValidationCheck;

import java.util.List;

public class TestFieldValidator implements ValidationCheck {

    @Override
    public Boolean validate(List<Attribute> attributes, List<ValidationResult> validationResultList) {
        final CHECKLISTSETDocument checklistsetDocument = CHECKLISTSETDocument.Factory.newInstance();
        final ChecklistType checklistType = checklistsetDocument.addNewCHECKLISTSET().addNewCHECKLIST();
        return null;
    }
}
