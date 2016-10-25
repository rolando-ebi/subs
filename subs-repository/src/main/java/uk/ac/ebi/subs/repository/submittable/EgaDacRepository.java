package uk.ac.ebi.subs.repository.submittable;

import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.EgaDac;

public interface EgaDacRepository extends MongoRepository<EgaDac, String> {
}
