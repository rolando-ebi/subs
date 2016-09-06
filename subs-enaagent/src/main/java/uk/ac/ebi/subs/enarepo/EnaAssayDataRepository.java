package uk.ac.ebi.subs.enarepo;


import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.AssayData;
import uk.ac.ebi.subs.data.submittable.Study;

public interface EnaAssayDataRepository extends MongoRepository<AssayData,String>{
}
