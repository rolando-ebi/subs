package uk.ac.ebi.subs.repository.projections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;
import uk.ac.ebi.subs.repository.model.ProcessingStatus;

import java.util.Date;

@Projection(name = "withAlias", types = ProcessingStatus.class)
public interface ProcessingStatusWithAlias {

    String getStatus();

    String getSubmittableType();

    String getArchive();

    String getAlias();

    Date getLastModifiedDate();

    String getLastModifiedBy();

    String getAccession();
}