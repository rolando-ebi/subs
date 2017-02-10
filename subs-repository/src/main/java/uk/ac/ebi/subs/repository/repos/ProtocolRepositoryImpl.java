package uk.ac.ebi.subs.repository.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.Protocol;
import uk.ac.ebi.subs.repository.repos.support.SubmittablesAggregateSupport;

@Component
public class ProtocolRepositoryImpl implements SubmittableRepositoryCustom<Protocol> {

    SubmittablesAggregateSupport<Protocol> aggregateSupport;

    public ProtocolRepositoryImpl(@Autowired MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, Protocol.class);
    }

    @Override
    public Page<Protocol> submittablesInDomain(String domainName, Pageable pageable) {
        return aggregateSupport.itemsByDomain(domainName, pageable);
    }


}
