package uk.ac.ebi.subs.repository.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uk.ac.ebi.subs.repository.model.Submission;

import java.util.Date;

@Projection(name = "withStatus",types = Submission.class)
public interface SubmissionWithStatus {

    @Value("#{target.submissionStatus.status}")
    String getSubmissionStatus();

    @Value("#{target.team.name}")
    String getTeam();

    @Value("#{target.submitter.email}")
    String getSubmitter();

    Date getLastModifiedDate();
    String getLastModifiedBy();
}
