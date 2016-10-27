package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.Analysis;

public interface AnalysisRepository extends MongoRepository<Analysis, String> {
}
