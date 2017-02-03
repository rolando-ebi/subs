package uk.ac.ebi.subs.repository.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.subs.data.Submission;

@CompoundIndexes({
        @CompoundIndex(name = "domain_alias", def = "{ 'domain.name': 1, 'alias': 1 }"),
        @CompoundIndex(name = "accession", def = "{ 'accession': 1}"),
        @CompoundIndex(name = "submissionId_status", def = "{ 'submission.$id': 1, 'status': 1}")
})
@Document
public class Assay extends uk.ac.ebi.subs.data.submittable.Assay implements StoredSubmittable{

    @DBRef
    private Submission submission;

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

}
