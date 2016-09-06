package uk.ac.ebi.subs.validation.checklist;

import org.apache.xmlbeans.XmlException;

import java.io.IOException;
import java.util.Set;

public abstract class ChecklistValidatorFactory {
    public abstract ChecklistValidator createChecklistValidator(String checklistId) throws IllegalArgumentException, XmlException, IOException;

    public static ChecklistValidatorFactory newInstance() throws IOException, XmlException {
        ChecklistValidatorFactory checklistValidatorFactory = null;
        try {
            checklistValidatorFactory = new FTPChecklistValidatorFactoryImpl();
        } catch (Exception e) {
            checklistValidatorFactory = new ChecklistValidatorFactoryImpl();
        }
        return checklistValidatorFactory;
    }

    public static ChecklistValidatorFactory newInstance(Class checklistValidatorFactoryClass) throws IllegalAccessException, InstantiationException {
        if (ChecklistValidatorFactory.class.isAssignableFrom(checklistValidatorFactoryClass)) {
            final ChecklistValidatorFactory checklistValidatorFactory = (ChecklistValidatorFactory) checklistValidatorFactoryClass.newInstance();
            return checklistValidatorFactory;
        } else {
            throw new InstantiationException("Class " + checklistValidatorFactoryClass.getName() + " does not implement the ChecklistValidatorFactory interface");
        }
    }

    public abstract Set<String> getChecklistIDSet();


}
