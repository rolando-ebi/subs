package uk.ac.ebi.subs.repository.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.EgaDataset;
import uk.ac.ebi.subs.repository.repos.support.SubmittablesAggregateSupport;

@Component
public class EgaDatasetRepositoryImpl implements SubmittableRepositoryCustom<EgaDataset> {

    SubmittablesAggregateSupport<EgaDataset> aggregateSupport;

    public EgaDatasetRepositoryImpl(@Autowired MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, EgaDataset.class);
    }

    @Override
    public Page<EgaDataset> submittablesInDomain(String domainName, Pageable pageable) {
        return aggregateSupport.itemsByDomain(domainName, pageable);
    }


}
