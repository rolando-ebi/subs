package uk.ac.ebi.subs.repository.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import java.util.Objects;

@CompoundIndexes({
        @CompoundIndex(name = "domain_submission", def = "{ 'domain.name': 1, 'submissionId': 1}")
})
public abstract class AbstractSubmissionNestedDocument<T> {

    // part of the resource path (domain / submission )
    String domainName;
    String submissionId;

    // single ID to use on updates - independent of the object accession
    @Id String id;


    public void setLocation(String domainName,String submissionId, String id){
        this.domainName = domainName;
        this.submissionId = submissionId;
        this.id = id;
    }

    public void setLocation(String domainName,String submissionId){
        this.domainName = domainName;
        this.submissionId = submissionId;
    }


    public void populate(String domainName,String submissionId, String id, T document){
        this.setLocation(domainName,submissionId,id);
        this.setDocument(document);
    }

    public void populate(String domainName,String submissionId, T document){
        this.setLocation(domainName,submissionId);
        this.setDocument(document);
    }


    public abstract T getDocument();

    public abstract void setDocument(T document);

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(String submissionId) {
        this.submissionId = submissionId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractSubmissionNestedDocument<?> that = (AbstractSubmissionNestedDocument<?>) o;
        return Objects.equals(domainName, that.domainName) &&
                Objects.equals(submissionId, that.submissionId) &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domainName, submissionId, id);
    }

    @Override
    public String toString() {
        return "AbstractSubmissionNestedDocument{" +
                "domainName='" + domainName + '\'' +
                ", submissionId='" + submissionId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
