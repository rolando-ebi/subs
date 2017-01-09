package uk.ac.ebi.subs.frontend.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import uk.ac.ebi.subs.data.submittable.EgaDacPolicy;
import uk.ac.ebi.subs.repository.submittable.EgaDacPolicyRepository;

@Component
public class EgaDacPolicyValidator implements Validator {

    @Autowired
    private CoreSubmittableValidationHelper coreSubmittableValidationHelper;
    @Autowired
    private EgaDacPolicyRepository repository;


    @Override
    public boolean supports(Class<?> clazz) {
        return EgaDacPolicy.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        EgaDacPolicy target = (EgaDacPolicy) object;
        coreSubmittableValidationHelper.validate(target, repository, errors);
    }
}
