package uk.ac.ebi.subs.repository.security;

import org.springframework.security.access.prepost.PostAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PostAuthorize("@authorizeUser.isAdminUser(authentication.details) or returnObject == null or hasRole(returnObject.team.name)")
public @interface PostAuthorizeReturnObjectHasTeamName {}
