package uk.ac.ebi.subs.frontend.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.status.ProcessingStatus;
import uk.ac.ebi.subs.repository.SubmissionRepository;
import uk.ac.ebi.subs.repository.model.StoredSubmittable;

import java.util.UUID;

@Component
public class CoreSubmittableEventHelper {

    @Autowired
    private SubmissionRepository submissionRepository;

    /**
     * Give submittables an ID and draft status on creation
     *
     * @param submittable
     */
    public void beforeCreate(StoredSubmittable submittable) {
        submittable.setId(UUID.randomUUID().toString());
        submittable.setStatus(ProcessingStatus.Draft);

        if (submittable.getSubmission() != null) {

            submittable.setDomain(submittable.getSubmission().getDomain());

        }
    }
}
