package uk.ac.ebi.subs.frontend.validators;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.status.Status;
import uk.ac.ebi.subs.data.submittable.Sample;
import uk.ac.ebi.subs.data.submittable.Submittable;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.submittable.SampleRepository;

import java.util.Collection;

@Component
public class SampleValidator extends AbstractSubmittableValidator {

    private SampleRepository sampleRepository;

    @Override
    Submittable getCurrentVersion(String id) {
        return sampleRepository.findOne(id);
    }

    @Autowired
    public SampleValidator(
            SampleRepository sampleRepository,
            SubmissionRepository submissionRepository,
            Collection<Status> processingStatuses,
            Collection<Status> releaseStatuses) {
        super(submissionRepository, processingStatuses, releaseStatuses);
        this.sampleRepository = sampleRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sample.class.isAssignableFrom(clazz);

    }


}
