package uk.ac.ebi.subs.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.subs.data.status.ProcessingStatus;
import uk.ac.ebi.subs.data.status.ReleaseStatus;
import uk.ac.ebi.subs.data.status.Status;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Configuration
public class StatusConfiguration {


    @Bean
    public List<Status> releaseStatuses() {
        List<Status> statuses = Arrays.asList(
                Status.build(ReleaseStatus.Draft, "In preparation")
                        .addTransition(ReleaseStatus.Private, true),

                Status.build(ReleaseStatus.Private, "Stored in an archive but not released yet")
                        .addTransition(ReleaseStatus.Cancelled, true)
                        .addTransition(ReleaseStatus.Public, false),

                Status.build(ReleaseStatus.Cancelled, "Exists in an archive but will not be released")
                        .addTransition(ReleaseStatus.Private, true),

                Status.build(ReleaseStatus.Public, "Available through an archive")
                    .addTransition(ReleaseStatus.Suppressed,false)
                    .addTransition(ReleaseStatus.Killed,false),

                Status.build(ReleaseStatus.Suppressed, "Data available but not findable without the accession")
                    .addTransition(ReleaseStatus.Public,false),

                Status.build(ReleaseStatus.Killed,"Data not available and not finable without the accession")

        );

        return statuses;
    }

    @Bean
    public List<Status> processingStatuses() {
        List<Status> statuses = Arrays.asList(

            Status.build(ProcessingStatus.Draft,"In preparation")
                .addTransition(ProcessingStatus.Submitted,true),

            Status.build(ProcessingStatus.Submitted,"User has submitted document for storage by archives")
                .addTransition(ProcessingStatus.Dispatched),

            Status.build(ProcessingStatus.Dispatched,"USI has dispatched document to an archive")
                .addTransition(ProcessingStatus.Received),

            Status.build(ProcessingStatus.Received,"Archive has received document")
                .addTransition(ProcessingStatus.Curation)
                .addTransition(ProcessingStatus.Processing),

            Status.build(ProcessingStatus.Curation,"Curation team is reviewing document")
                .addTransition(ProcessingStatus.Accepted)
                .addTransition(ProcessingStatus.ActionRequired),

            Status.build(ProcessingStatus.Accepted,"Curation team has accepted document")
                .addTransition(ProcessingStatus.Processing),

            Status.build(ProcessingStatus.ActionRequired,"Curation team have requested changes or additional information")
                .addTransition(ProcessingStatus.Submitted,true),

            Status.build(ProcessingStatus.Processing,"Archive is processing document")
                .addTransition(ProcessingStatus.Done),

            Status.build(ProcessingStatus.Done,"Archive has stored document")
        );


        return statuses;
    }

}
