package uk.ac.ebi.subs.frontend.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.submittable.Submittable;

import java.util.UUID;

@Component
public class CoreSubmittableEventHelper {

    public void beforeCreate(Submittable submittable){
        submittable.setId(UUID.randomUUID().toString());
    }
}
