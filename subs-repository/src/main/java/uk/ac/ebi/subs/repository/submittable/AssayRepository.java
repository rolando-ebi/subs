package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.Assay;

public interface AssayRepository extends MongoRepository<Assay, String> {
}
