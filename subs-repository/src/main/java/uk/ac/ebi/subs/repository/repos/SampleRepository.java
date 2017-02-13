package uk.ac.ebi.subs.repository.repos;

import uk.ac.ebi.subs.repository.model.Sample;

public interface SampleRepository extends SubmittableRepository<Sample> {

    Sample findByAccession(String accession);
}
