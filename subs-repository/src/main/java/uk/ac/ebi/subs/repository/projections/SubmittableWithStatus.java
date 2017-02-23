package uk.ac.ebi.subs.repository.projections;

import org.springframework.data.rest.core.config.Projection;
import uk.ac.ebi.subs.data.component.Archive;
import uk.ac.ebi.subs.data.component.Domain;
import uk.ac.ebi.subs.repository.model.*;

import java.util.Date;

@Projection(name = "withStatus",types = {
        Analysis.class,
        Assay.class,
        AssayData.class,
        EgaDac.class,
        EgaDacPolicy.class,
        EgaDataset.class,
        Project.class,
        Protocol.class,
        Sample.class,
        SampleGroup.class,
        Study.class
})
public interface SubmittableWithStatus {

    ProcessingStatus getProcessingStatus();
    Long getVersion();
    Date getCreatedDate();
    Date getLastModifiedDate();
    String getCreatedBy();
    String getLastModifiedBy();
    Archive getArchive();
    String getAccession();
    Domain getDomain();
    String getAlias();
    String getTitle();
    String getDescription();

}
