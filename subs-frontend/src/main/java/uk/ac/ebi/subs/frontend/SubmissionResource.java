package uk.ac.ebi.subs.frontend;

/**
 * Created by davidr on 24/08/2016.
 */
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;
import uk.ac.ebi.subs.data.submittable.Submission;

public class SubmissionResource extends ResourceSupport {
    private final Submission content;

    @JsonCreator
    public SubmissionResource(@JsonProperty("content") Submission content){
        this.content = content;
    }

    public Submission getContent() {
        return content;
    }
}
