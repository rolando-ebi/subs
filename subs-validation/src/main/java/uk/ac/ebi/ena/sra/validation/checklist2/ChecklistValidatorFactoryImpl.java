package uk.ac.ebi.ena.sra.validation.checklist2;

import org.apache.xmlbeans.XmlException;

import uk.ac.ebi.ena.sra.xml.CHECKLISTSETDocument;
import uk.ac.ebi.ena.sra.xml.ChecklistSetType;
import uk.ac.ebi.ena.sra.xml.ChecklistType;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ChecklistValidatorFactoryImpl extends ChecklistValidatorFactory {
    public static String DEFAULT_CHECKLIST_RESOURCE_FILE = "/uk/ac/ebi/subs/validator/checklist/checklist.xml";
    Map<String, ChecklistType> checklistTypeMap = new HashMap<String, ChecklistType>();


    public ChecklistValidatorFactoryImpl() throws IOException, XmlException {
        this(ChecklistValidatorFactoryImpl.class.getResourceAsStream(DEFAULT_CHECKLIST_RESOURCE_FILE));
    }

    public ChecklistValidatorFactoryImpl(InputStream inputStream) throws IOException, XmlException {
        checklistTypeMap = createChecklistTypeMap(inputStream);
    }

    public ChecklistValidator createChecklistValidator(String checklistId) throws IllegalArgumentException, XmlException {
        ChecklistType checklistType = checklistTypeMap.get(checklistId);
        if (checklistType == null) {
            throw new IllegalArgumentException("Unknown checklist id " + checklistId);
        } else {
            return new ChecklistValidatorImpl(checklistType);
        }
    }

    public Map<String, ChecklistType> getChecklistTypeMap() {
        return checklistTypeMap;
    }

    private static Map<String, ChecklistType> createChecklistTypeMap(InputStream inputStream) throws IOException, XmlException {
        Map<String, ChecklistType> checklistTypeMap = new HashMap<>();
        CHECKLISTSETDocument checklistsetDocument = CHECKLISTSETDocument.Factory.parse(inputStream);
        final ChecklistSetType checklistSetType = checklistsetDocument.getCHECKLISTSET();
        if (checklistSetType != null && checklistSetType.getCHECKLISTArray() != null) {
            final ChecklistType[] checklistArray = checklistSetType.getCHECKLISTArray();
            for (ChecklistType checklistType : checklistArray) {
                checklistTypeMap.put(checklistType.getAccession(), checklistType);
            }
        }
        return checklistTypeMap;
    }

}
