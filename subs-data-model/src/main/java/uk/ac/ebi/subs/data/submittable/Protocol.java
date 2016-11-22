package uk.ac.ebi.subs.data.submittable;

import org.springframework.data.annotation.Id;
import uk.ac.ebi.subs.data.component.AbstractSubsRef;
import uk.ac.ebi.subs.data.component.ProtocolRef;

public class Protocol extends AbstractSubsEntity<Protocol> {

    @Id
    String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    protected AbstractSubsRef<Protocol> newRef() {
        return new ProtocolRef();
    }

    @Override
    public String toString() {
        return "Protocol{} " + super.toString();
    }
}