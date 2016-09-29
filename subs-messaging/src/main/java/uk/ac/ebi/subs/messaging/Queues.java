package uk.ac.ebi.subs.messaging;

public class Queues {


    public static final String SUBMISSION_MONITOR = "usi-submission-monitor";
    public static final String SUBMISSION_MONITOR_ROUTING_KEY = Topics.EVENT_SUBMISSION_PROCESSED;


    public static final String SUBMISSION_DISPATCHER = "usi-submission-dispatcher";
    public static final String SUBMISSION_DISPATCHER_ROUTING_KEY = "usi.submission.flow.*";

    public static final String BIOSAMPLES_AGENT = "usi-submission-agent-biosamples";
    public static final String ENA_AGENT = "usi-submission-agents-ena";
    public static final String AE_AGENT = "usi-submission-agents-arrayexpress";

}
