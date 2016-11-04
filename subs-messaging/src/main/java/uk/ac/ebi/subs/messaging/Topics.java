package uk.ac.ebi.subs.messaging;

/**
 * Created by davidr on 25/08/2016.
 */
public class Topics {
    /**
     * The User has submitted a Submission
     */
    public static final String EVENT_SUBMISSION_SUBMITTED = "usi.submissionenvelope.submitted";


    /**
     * The monitor has updated a Submission
     */
    public static final String EVENT_SUBMISSION_UPDATED = "usi.submissionenvelope.updated";

    /**
     * The dispatcher thinks the submission status should change
     */
    public static final String EVENT_SUBMISSION_STATUS_CHANGE = "usi.certificate.submissionstatus.update";


    /**
     * An Agent has produced AgentResults
     */
    public static final String EVENT_SUBMISSION_AGENT_RESULTS = "usi.agentresults.produced";


    /**
     * Supporting information required from BioSamples
     */
    public static final String EVENT_SUBMISSION_NEEDS_SAMPLES = "usi.submissionenvelope.supportinginformation.samples";

    /**
     * Supporting information provided
     */
    public static final String EVENT_SUBISSION_SUPPORTING_INFO_PROVIDED = "usi.submissionenvelope.supportinginformation.provided";

    /**
     * Samples processing required
     */
    public static final String SAMPLES_PROCESSING = "usi.submissionenvelope.dispatched.biosamples";

    /**
     * ENA processing required
     */
    public static final String ENA_PROCESSING = "usi.submissionenvelope.dispatched.ena";

    /**
     * AE processing required
     */
    public static final String AE_PROCESSING = "usi.submissionenvelope.dispatched.arrayexpress";


}
