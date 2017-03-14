package uk.ac.ebi.subs.repository.repos.status;

import java.util.Map;


public interface ProcessingStatusRepositoryCustom {

    Map<String, Integer> summariseSubmissionStatus(String submissionId);

    Map<String, Map<String, Integer>> summariseSubmissionStatusAndType(String submissionId);
}
