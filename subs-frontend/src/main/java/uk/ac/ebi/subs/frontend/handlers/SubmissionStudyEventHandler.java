package uk.ac.ebi.subs.frontend.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.repository.submittable.StudyRepository;

import java.util.UUID;


/**
 * Repo event handler for studies nested in a submission
 *
 */
@RepositoryEventHandler(Study.class)
public class SubmissionStudyEventHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudyRepository studyRepository;

    @HandleBeforeCreate
    public void handleBeforeCreate(Study study){
        logger.warn("create");
        study.setId(UUID.randomUUID().toString());
    }


}
