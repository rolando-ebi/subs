package uk.ac.ebi.subs.repository.repos.submittables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.AssayData;
import uk.ac.ebi.subs.repository.repos.submittables.support.SubmittablesAggregateSupport;

@Component
public class AssayDataRepositoryImpl implements SubmittableRepositoryCustom<AssayData> {

    private SubmittablesAggregateSupport<AssayData> aggregateSupport;

    public AssayDataRepositoryImpl(MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, AssayData.class);

    }

    @Override
    public Page<AssayData> submittablesInTeam(String teamName, Pageable pageable) {
        return aggregateSupport.itemsByTeam(teamName, pageable);
    }

}
