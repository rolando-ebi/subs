package uk.ac.ebi.subs.frontend.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.repository.SubmissionRepository;

@Component
public class SampleValidator extends AbstractSubmittableValidator {

    @Autowired
    public SampleValidator(SubmissionRepository submissionRepository) {
        super(submissionRepository);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sample.class.isAssignableFrom(clazz);

    }


}
