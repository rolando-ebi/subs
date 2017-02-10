package uk.ac.ebi.subs.repository.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.Sample;
import uk.ac.ebi.subs.repository.repos.support.SubmittablesAggregateSupport;

@Component
public class SampleRepositoryImpl implements SubmittableRepositoryCustom<Sample> {

    SubmittablesAggregateSupport<Sample> aggregateSupport;

    public SampleRepositoryImpl(@Autowired MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, Sample.class);
    }

    @Override
    public Page<Sample> submittablesInDomain(String domainName, Pageable pageable) {
        return aggregateSupport.itemsByDomain(domainName, pageable);
    }


}
