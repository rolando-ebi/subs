package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.AssayData;

public interface AssayDataRepository extends MongoRepository<AssayData, String> {
}
