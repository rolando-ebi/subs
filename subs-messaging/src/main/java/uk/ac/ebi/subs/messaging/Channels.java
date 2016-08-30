package uk.ac.ebi.subs.messaging;

/**
 * Created by davidr on 25/08/2016.
 */
public class Channels {
    /**
     * The User has submitted a Submission
     */
    public static final String SUBMISSION_SUBMITTED = "usi.submission.submitted";

    /**
     * An Agent has processed a Submission
     */
    public static final String SUBMISSION_PROCESSED = "usi.submission.processed";

    /**
     * Samples processing required
     */
    public static final String SAMPLES_PROCESSING = "usi.submission.samples";

    /**
     * ENA processing required
     */
    public static final String ENA_PROCESSING = "usi.submission.ena";

    /**
     * AE processing required
     */
    public static final String AE_PROCESSING = "usi.submission.arrayexpress";


}
