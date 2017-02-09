package uk.ac.ebi.subs.repository.repos.support;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import uk.ac.ebi.subs.repository.model.StoredSubmittable;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class SubmittablesAggregateSupport<T extends StoredSubmittable> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private MongoTemplate mongoTemplate;
    private Class<T> clazz;

    public SubmittablesAggregateSupport(MongoTemplate mongoTemplate, Class<T> clazz) {
        this.mongoTemplate = mongoTemplate;
        this.clazz = clazz;
    }


    public Page<T> itemsByDomain(String domainName, Pageable pageable) {
        List resultsList = getLimitedItemListByDomain(domainName, pageable);
        long totalItemsCount = getTotalItemCountByDomain(domainName);
        return new PageImpl(resultsList, pageable, totalItemsCount);
    }

    public long getTotalItemCountByDomain(String domainName) {
        AggregationResults aggregationResults = mongoTemplate.aggregate(Aggregation.newAggregation(
                domainMatchOperation(domainName),
                groupByAlias(),
                count().as("count")
        ), clazz, clazz);

        Object results = aggregationResults.getRawResults().get("result");

        if (results != null && results instanceof BasicDBList) {
            BasicDBList resultsList = (BasicDBList) results;
            return ((BasicDBObject) resultsList.get(0)).getLong("count");
        }

        return -1;
    }

    private List getLimitedItemListByDomain(String domainName, Pageable pageable) {
        final List resultsList = new ArrayList();

        AggregationResults aggregationResults = mongoTemplate.aggregate(Aggregation.newAggregation(
                domainMatchOperation(domainName),
                sortAliasCreatedDate(),
                groupByAliasWithFirstItem(),
                skip((long) pageable.getOffset()),
                limit((long) pageable.getPageSize())
        ), clazz, clazz);

        /*
            TODO with a once we have mongo 3.4 db we can use ReplaceRootOperation replaceRootOp = replaceRoot("first");
            after limit to skip the awkward extraction step below

         */
        Object results = aggregationResults.getRawResults().get("result");

        if (results != null && results instanceof BasicDBList) {
            BasicDBList resultSet = (BasicDBList) results;
            resultSet.stream()
                    .map(o -> (DBObject) ((DBObject) o).get("first"))
                    .map(first -> mongoTemplate.getConverter().read(clazz, first))
                    .forEachOrdered(o -> resultsList.add(o));
        }
        return resultsList;
    }

    private GroupOperation groupByAliasWithFirstItem() {
        return group("alias").first("$$ROOT").as("first");
    }

    private GroupOperation groupByAlias() {
        return group("alias");
    }

    private SortOperation sortAliasCreatedDate() {
        return sort(Sort.Direction.DESC, "alias").and(Sort.Direction.DESC, "createdDate");
    }

    private MatchOperation domainMatchOperation(String domainName) {
        return match(where("domain.name").is(domainName));
    }

}
