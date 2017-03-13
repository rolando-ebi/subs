package uk.ac.ebi.subs.repository.security;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import uk.ac.ebi.subs.data.component.Team;
import uk.ac.ebi.subs.repository.model.ProcessingStatus;
import uk.ac.ebi.subs.repository.model.StoredSubmittable;
import uk.ac.ebi.subs.repository.model.Submission;
import uk.ac.ebi.subs.repository.model.SubmissionStatus;
import uk.ac.ebi.subs.repository.repos.SubmissionRepository;
import uk.ac.ebi.subs.repository.repos.submittables.SubmittableRepository;
import uk.ac.ebi.tsc.aap.client.model.Domain;
import uk.ac.ebi.tsc.aap.client.model.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Component
public class AuthorizeUser {

    public AuthorizeUser(List<SubmittableRepository<?>> submissionContentsRepositories) {
        this.submissionContentsRepositories = submissionContentsRepositories;
    }

    private List<SubmittableRepository<?>> submissionContentsRepositories;
    private SubmissionRepository submissionRepository;

    public static final String ADMIN_USER_DOMAIN_NAME = "usiAdmin";

    public Boolean isAdminUser(User user) {
        return isTeamNameInUser(user,ADMIN_USER_DOMAIN_NAME);
    }

    public Boolean canUseProcessingStatus(User user, ProcessingStatus processingStatus) {
        Team processingStatusTeam = processingStatusTeam(processingStatus);

        return isTeamNameInUser(user, processingStatusTeam.getName());
    }

    public Boolean canUseSubmissionId(User user, String submissionId){
        Team submissionTeam = submissionIdTeam(submissionId);
        return isTeamNameInUser(user, submissionTeam.getName());
    }


    public Boolean canUseSubmissionStatus(User user,SubmissionStatus submissionStatus){
        Team submissionStatusTeam = submissionStatusTeam(submissionStatus);
        return isTeamNameInUser(user,submissionStatusTeam.getName());
    }

    public Team processingStatusTeam(ProcessingStatus processingStatus) {
        Optional<Team> optionalTeam = submissionContentsRepositories //TODO can we be more targetted here?
                .stream()
                .map(repo -> (StoredSubmittable) repo.findOne(processingStatus.getSubmittableId()))
                .filter(Objects::nonNull)
                .map(storedSubmittable -> storedSubmittable.getTeam())
                .filter(Objects::nonNull)
                .findFirst();

        if (!optionalTeam.isPresent()) {
            throw new IllegalStateException("Cannot find owning team for processing status during authorization " + processingStatus.toString());
        }
        return optionalTeam.get();
    }

    public Team submissionStatusTeam(SubmissionStatus submissionStatus) {
        Submission submission = submissionRepository.findBySubmissionStatusId(submissionStatus.getId());

        if (submission == null) throw new ResourceNotFoundException();

        return submission.getTeam();
    }

    public Team submissionIdTeam(String submissionId){
        Submission submission = submissionRepository.findOne(submissionId);

        if (submission == null) throw new ResourceNotFoundException();

        return submission.getTeam();
    }

    private boolean isTeamNameInUser(User user, String teamName) {
        Optional<Domain> optionalDomain = user.getDomains()
                .stream()
                .filter(new DomainNameMatcher(teamName))
                .findFirst();

        return optionalDomain.isPresent();
    }


    private class DomainNameMatcher implements Predicate<Domain> {
        private String acceptableName;

        public DomainNameMatcher(String acceptableName) {
            this.acceptableName = acceptableName;
        }

        @Override
        public boolean test(Domain domain) {
            return domain.getDomainName().equals(acceptableName);
        }
    }
}
