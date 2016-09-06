package uk.ac.ebi.subs.enarepo;


import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.Assay;
import uk.ac.ebi.subs.data.submittable.Study;

public interface EnaAssayRepository extends MongoRepository<Assay,String>{
}
