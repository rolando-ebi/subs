package uk.ac.ebi.ena.sra.validation.checklist2.field;

import uk.ac.ebi.embl.api.validation.ValidationResult;
import uk.ac.ebi.ena.sra.validation.checklist2.Attribute;
import uk.ac.ebi.ena.sra.validation.checklist2.ValidationCheck;
import uk.ac.ebi.ena.sra.xml.CHECKLISTSETDocument;
import uk.ac.ebi.ena.sra.xml.ChecklistType;

import java.util.List;

public class TestFieldValidator implements ValidationCheck {

    @Override
    public Boolean validate(List<Attribute> attributes, List<ValidationResult> validationResultList) {
        final CHECKLISTSETDocument checklistsetDocument = CHECKLISTSETDocument.Factory.newInstance();
        final ChecklistType checklistType = checklistsetDocument.addNewCHECKLISTSET().addNewCHECKLIST();
        return null;
    }
}
