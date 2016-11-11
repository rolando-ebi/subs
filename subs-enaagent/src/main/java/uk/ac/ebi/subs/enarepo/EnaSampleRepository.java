package uk.ac.ebi.subs.enarepo;


import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.submittable.Sample;

public interface EnaSampleRepository extends MongoRepository<Sample, String>{

    Sample findByAccession(String accession);
}
