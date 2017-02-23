package uk.ac.ebi.subs.repository.projections;

import org.springframework.data.annotation.*;
import org.springframework.data.rest.core.config.Projection;
import uk.ac.ebi.subs.data.component.Domain;
import uk.ac.ebi.subs.data.component.Submitter;
import uk.ac.ebi.subs.repository.model.Submission;
import uk.ac.ebi.subs.repository.model.SubmissionStatus;

import java.util.Date;

@Projection(name = "withStatus",types = Submission.class)
public interface SubmissionWithStatus {
    SubmissionStatus getSubmissionStatus();
    Domain getDomain();
    Submitter getSubmitter();
    Long getVersion();
    Date getCreatedDate();
    Date getLastModifiedDate();
    String getCreatedBy();
    String getLastModifiedBy();
}
