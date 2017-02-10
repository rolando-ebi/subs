package uk.ac.ebi.subs.repository.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.Analysis;
import uk.ac.ebi.subs.repository.repos.support.SubmittablesAggregateSupport;

@Component
public class AnalysisRepositoryImpl implements SubmittableRepositoryCustom<Analysis> {

    SubmittablesAggregateSupport<Analysis> aggregateSupport;

    public AnalysisRepositoryImpl(@Autowired MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, Analysis.class);
    }

    @Override
    public Page<Analysis> submittablesInDomain(String domainName, Pageable pageable) {
        return aggregateSupport.itemsByDomain(domainName, pageable);
    }


}
