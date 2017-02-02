package uk.ac.ebi.subs.frontend.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.submittable.Study;


/**
 * Repo event handler for studies nested in a submission
 */
@Component
@RepositoryEventHandler(Study.class)
public class StudyEventHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CoreSubmittableEventHelper coreSubmittableEventHelper;

    @HandleBeforeCreate
    public void handleBeforeCreate(Study src) {
        logger.info("event before create {}", src);
        coreSubmittableEventHelper.beforeCreate(src);
    }
}
