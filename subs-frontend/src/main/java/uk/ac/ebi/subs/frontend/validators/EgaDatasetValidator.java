package uk.ac.ebi.subs.frontend.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import uk.ac.ebi.subs.data.submittable.EgaDataset;
import uk.ac.ebi.subs.repository.repos.EgaDatasetRepository;

@Component
public class EgaDatasetValidator implements Validator {

    @Autowired
    private CoreSubmittableValidationHelper coreSubmittableValidationHelper;
    @Autowired
    private EgaDatasetRepository repository;


    @Override
    public boolean supports(Class<?> clazz) {
        return EgaDataset.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        EgaDataset target = (EgaDataset) object;
        coreSubmittableValidationHelper.validate(target, repository, errors);
    }
}

