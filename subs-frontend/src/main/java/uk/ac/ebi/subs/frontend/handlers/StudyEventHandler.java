package uk.ac.ebi.subs.frontend.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uk.ac.ebi.subs.data.submittable.Study;
import uk.ac.ebi.subs.repository.submittable.StudyRepository;

import java.util.UUID;


/**
 * Repo event handler for studies nested in a submission
 *
 */
@Component
@RepositoryEventHandler(Study.class)
public class StudyEventHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StudyRepository studyRepository;

    @HandleBeforeCreate
    public void handleBeforeCreate(Study study){
        logger.warn("create");
        study.setId(UUID.randomUUID().toString());
    }

    @HandleBeforeSave
    public void handleBeforeSave(Study study){
        logger.warn("save");
        study.setId(UUID.randomUUID().toString());
    }

    @HandleAfterCreate
    public void handleAfterCreate(Study study){
        logger.warn("aftercreate");
    }

    @HandleAfterSave
    public void handleAfterSave(Study study){
        logger.warn("aftersave");
    }

}
