package uk.ac.ebi.subs.repository.repos.submittables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.SampleGroup;
import uk.ac.ebi.subs.repository.repos.submittables.support.SubmittablesAggregateSupport;

@Component
public class SampleGroupRepositoryImpl implements SubmittableRepositoryCustom<SampleGroup> {

    private SubmittablesAggregateSupport<SampleGroup> aggregateSupport;

    public SampleGroupRepositoryImpl(MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, SampleGroup.class);
    }

    @Override
    public Page<SampleGroup> submittablesInDomain(String domainName, Pageable pageable) {
        return aggregateSupport.itemsByDomain(domainName, pageable);
    }

}
