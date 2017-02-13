package uk.ac.ebi.subs.repository.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.Project;
import uk.ac.ebi.subs.repository.repos.support.SubmittablesAggregateSupport;

@Component
public class ProjectRepositoryImpl implements SubmittableRepositoryCustom<Project> {

    SubmittablesAggregateSupport<Project> aggregateSupport;

    public ProjectRepositoryImpl(@Autowired MongoTemplate mongoTemplate) {
        this.aggregateSupport = new SubmittablesAggregateSupport<>(mongoTemplate, Project.class);
    }

    @Override
    public Page<Project> submittablesInDomain(String domainName, Pageable pageable) {
        return aggregateSupport.itemsByDomain(domainName, pageable);
    }


}
