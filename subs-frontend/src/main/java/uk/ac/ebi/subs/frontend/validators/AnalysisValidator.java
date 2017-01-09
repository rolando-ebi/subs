package uk.ac.ebi.subs.frontend.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import uk.ac.ebi.subs.data.submittable.Analysis;
import uk.ac.ebi.subs.repository.submittable.AnalysisRepository;

@Component
public class AnalysisValidator implements Validator {

    @Autowired
    private CoreSubmittableValidationHelper coreSubmittableValidationHelper;
    @Autowired
    private AnalysisRepository analysisRepository;


    @Override
    public boolean supports(Class<?> clazz) {
        return Analysis.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Analysis analysis = (Analysis) target;
        coreSubmittableValidationHelper.validate(analysis, analysisRepository, errors);
    }
}
