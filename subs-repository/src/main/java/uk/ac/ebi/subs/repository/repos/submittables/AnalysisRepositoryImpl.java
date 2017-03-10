package uk.ac.ebi.subs.repository.repos.submittables;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.Analysis;
import uk.ac.ebi.subs.repository.repos.submittables.support.SubmittablesAggregateSupport;

@Component
public class AnalysisRepositoryImpl implements SubmittableRepositoryCustom<Analysis> {

    private SubmittablesAggregateSupport<Analysis> aggregateSupport;

    public AnalysisRepositoryImpl(MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, Analysis.class);
    }

    @Override
    public Page<Analysis> submittablesInTeam(String teamName, Pageable pageable) {
        return aggregateSupport.itemsByTeam(teamName, pageable);
    }


}
