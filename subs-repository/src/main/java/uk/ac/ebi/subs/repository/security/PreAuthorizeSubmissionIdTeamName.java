package uk.ac.ebi.subs.repository.security;


import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole(@authorizeUser.adminRole(),@authorizeUser.submissionIdTeam(#submissionId))")
public @interface PreAuthorizeSubmissionIdTeamName {
}
