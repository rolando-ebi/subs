package uk.ac.ebi.subs.messaging;

public class Queues {



    public static final String SUBMISSION_MONITOR = "usi-submission-monitor";
    public static final String SUBMISSION_MONITOR_ROUTING_KEY = Topics.EVENT_SUBMISSION_AGENT_RESULTS;

    public static final String SUBMISSION_DISPATCHER = "usi-submission-dispatcher";
    //will match usi.submissionenvelope.updated and usi.submissionenvelope.submitted, but not usi.submissionenvelope.dispatched.biosamples etc
    public static final String SUBMISSION_DISPATCHER_ROUTING_KEY = "usi.submissionenvelope.*";

    public static final String SUBMISSION_SUPPORTING_INFO_PROVIDED = "usi-submission-supp-inf-provided";
    public static final String SUBMISSION_SUPPORTING_INFO_PROVIDED_ROUTING_KEY = Topics.EVENT_SUBISSION_SUPPORTING_INFO_PROVIDED;

    public static final String SUBMISSION_SUPPORTING_INFO = "usi-submission-supp-inf";
    public static final String SUBMISSION_SUPPORTING_INFO_ROUTING_KEY = Topics.EVENT_SUBMISSION_SUBMITTED;

    public static final String SUBMISSION_NEEDS_SAMPLE_INFO = "usi-submission-support-biosamples";
    public static final String SUBMISSION_NEEDS_SAMPLE_INFO_ROUTING_KEY = Topics.EVENT_SUBMISSION_NEEDS_SAMPLES;

    public static final String BIOSAMPLES_AGENT = "usi-submission-agent-biosamples";
    public static final String ENA_AGENT = "usi-submission-agents-ena";
    public static final String AE_AGENT = "usi-submission-agents-arrayexpress";

}
