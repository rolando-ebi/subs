package uk.ac.ebi.subs.repository.repos.submittables;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.EgaDacPolicy;
import uk.ac.ebi.subs.repository.repos.submittables.support.SubmittablesAggregateSupport;

@Component
public class EgaDacPolicyRepositoryImpl implements SubmittableRepositoryCustom<EgaDacPolicy> {

    private SubmittablesAggregateSupport<EgaDacPolicy> aggregateSupport;

    public EgaDacPolicyRepositoryImpl(@Autowired MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, EgaDacPolicy.class);
    }

    @Override
    public Page<EgaDacPolicy> submittablesInDomain(String domainName, Pageable pageable) {
        return aggregateSupport.itemsByDomain(domainName, pageable);
    }
}
