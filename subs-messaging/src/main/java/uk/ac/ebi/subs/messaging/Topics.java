package uk.ac.ebi.subs.messaging;

/**
 * Created by davidr on 25/08/2016.
 */
public class Topics {
    /**
     * The User has submitted a Submission
     */
    public static final String EVENT_SUBMISSION_SUBMITTED = "usi.submission.flow.submitted";
    /**
     * An Agent has processed a Submission
     */
    public static final String EVENT_SUBMISSION_PROCESSED = "usi.submission.flow.processed";



    /**
     * Samples processing required
     */
    public static final String SAMPLES_PROCESSING = "usi.submission.agents.biosamples";

    /**
     * ENA processing required
     */
    public static final String ENA_PROCESSING = "usi.submission.agents.ena";

    /**
     * AE processing required
     */
    public static final String AE_PROCESSING = "usi.submission.agents.arrayexpress";


}
