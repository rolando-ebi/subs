package uk.ac.ebi.subs.repository.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import uk.ac.ebi.subs.data.Submission;

/*
    Caution - Spring data does not apply indexes from parent classes
     the index definition has to be in the child classes

    The compound indexes block below should in sync with the reference copy in AbstractSubsEntity
 */
@CompoundIndexes({
        @CompoundIndex(name = "domain_alias", def = "{ 'domain.name': 1, 'alias': 1 }"),
        @CompoundIndex(name = "accession", def = "{ 'accession': 1}"),
        @CompoundIndex(name = "submissionId_status", def = "{ 'submissionId': 1, 'status': 1}")
})
//@Document //TODO - there is a potential cyclic reference that is flagged up when you have @Document - reconsider design
public class Analysis extends uk.ac.ebi.subs.data.submittable.Analysis implements StoredSubmittable {

    @DBRef
    private Submission submission;

    public Submission getSubmission() {
        return submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
