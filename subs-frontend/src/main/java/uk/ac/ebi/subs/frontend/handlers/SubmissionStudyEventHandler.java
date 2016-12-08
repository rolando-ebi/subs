package uk.ac.ebi.subs.frontend.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import uk.ac.ebi.subs.repository.model.SubmissionStudy;
import uk.ac.ebi.subs.repository.repo.SubmissionStudyRepo;

import java.util.UUID;


/**
 * Repo event handler for studies nested in a submission
 *
 */
@RepositoryEventHandler(SubmissionStudy.class)
public class SubmissionStudyEventHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SubmissionStudyRepo submissionStudyRepo;

    @HandleBeforeCreate
    public void handleBeforeCreate(SubmissionStudy submissionStudy){
        logger.warn("create");
        submissionStudy.setId(UUID.randomUUID().toString());
    }


}
