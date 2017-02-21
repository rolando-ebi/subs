package uk.ac.ebi.subs.repository.repos.submittables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.Protocol;
import uk.ac.ebi.subs.repository.repos.submittables.support.SubmittablesAggregateSupport;

@Component
public class ProtocolRepositoryImpl implements SubmittableRepositoryCustom<Protocol> {

    private SubmittablesAggregateSupport<Protocol> aggregateSupport;

    public ProtocolRepositoryImpl(MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, Protocol.class);
    }

    @Override
    public Page<Protocol> submittablesInDomain(String domainName, Pageable pageable) {
        return aggregateSupport.itemsByDomain(domainName, pageable);
    }

}
