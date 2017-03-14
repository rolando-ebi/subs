package uk.ac.ebi.subs.repository.repos.status;


import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.repository.model.ProcessingStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Component
public class ProcessingStatusRepositoryImpl implements ProcessingStatusRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public ProcessingStatusRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Map<String, Integer> summariseSubmissionStatus(String submissionId) {
        Aggregation agg = Aggregation.newAggregation(
                submissionMatchOperation(submissionId),
                groupByStatus(),
                projectStatusCount()
        );

        AggregationResults<StatusSummary> aggregationResults = mongoTemplate.aggregate(
                agg, ProcessingStatus.class, StatusSummary.class
        );

        List<StatusSummary> statusSummaries = aggregationResults.getMappedResults();

        Map<String, Integer> statusCounts = new HashMap<>();

        for (StatusSummary statusSummary : statusSummaries) {
            statusCounts.put(statusSummary.getStatus(), statusSummary.getCount());
        }
        return statusCounts;
    }

    @Override
    public Map<String, Map<String, Integer>> summariseSubmissionStatusAndType(String submissionId) {
        Aggregation agg = Aggregation.newAggregation(
                submissionMatchOperation(submissionId),
                groupByTypeAndStatus(),
                projectTypeStatusCount()
        );

        AggregationResults<TypeStatusSummary> aggregationResults = mongoTemplate.aggregate(
                agg, ProcessingStatus.class, TypeStatusSummary.class
        );

        List<TypeStatusSummary> statusSummaries = aggregationResults.getMappedResults();

        Map<String, Map<String, Integer>> typeStatusCounts = new HashMap<>();

        for (TypeStatusSummary typeStatusSummary : statusSummaries) {

            if (!typeStatusCounts.containsKey(typeStatusSummary.getType())) {
                typeStatusCounts.put(typeStatusSummary.getType(), new HashMap<>());
            }

            typeStatusCounts
                    .get(typeStatusSummary.getType())
                    .put(typeStatusSummary.getStatus(), typeStatusSummary.getCount());

        }


        return typeStatusCounts;
    }

    private MatchOperation submissionMatchOperation(String submissionId) {
        return match(where("submissionId").is(submissionId));
    }

    private GroupOperation groupByStatus() {
        return group("status").count().as("count");
    }

    private ProjectionOperation projectStatusCount() {
        return project("count").and("_id").as("status");
    }

    private GroupOperation groupByTypeAndStatus() {
        return group("submittableType", "status").count().as("count");
    }

    private ProjectionOperation projectTypeStatusCount() {
        return project("count").and("_id.status").as("status").and("_id.submittableType").as("type");
    }


    public class StatusSummary {

        private String status;
        private int count;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public class TypeStatusSummary extends StatusSummary {
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
