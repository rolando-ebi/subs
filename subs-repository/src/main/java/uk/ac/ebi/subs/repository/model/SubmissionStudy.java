package uk.ac.ebi.subs.repository.model;


import uk.ac.ebi.subs.data.submittable.Study;

import java.util.Objects;

public class SubmissionStudy extends AbstractSubmissionNestedDocument<Study> {
    Study study;

    @Override
    public Study getDocument() {
        return study;
    }

    @Override
    public void setDocument(Study study) {
        this.study = study;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SubmissionStudy that = (SubmissionStudy) o;
        return Objects.equals(study, that.study);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), study);
    }

    @Override
    public String toString() {
        return "SubmissionStudy{" +
                "study=" + study +
                "} " + super.toString();
    }
}
