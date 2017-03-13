package uk.ac.ebi.subs.repository;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ebi.subs.repository.security.AuthorizeUser;
import uk.ac.ebi.tsc.aap.client.model.Domain;
import uk.ac.ebi.tsc.aap.client.model.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AuthorizeAdminUserTest {

    private AuthorizeUser authorizeUser;


    @Test
    public void adminUser() {
        User adminUser = createUser(AuthorizeUser.ADMIN_USER_DOMAIN_NAME);

        Boolean authorized = authorizeUser.isAdminUser(adminUser);

        assertThat(authorized, is(equalTo(true)));
    }

    @Test
    public void notAdminUser() {
        User normalUser = createUser("i-am-not-an-admin-user");

        Boolean authorized = authorizeUser.isAdminUser(normalUser);

        assertThat(authorized, is(equalTo(false)));
    }

    @Test
    public void emptyUser() {
        User emptyUser = createUser();

        Boolean authorized = authorizeUser.isAdminUser(emptyUser);

        assertThat(authorized, is(equalTo(false)));
    }

    @Test
    public void userWithManyDomains() {
        User user = createUser();

        IntStream.range(1, 10000).forEach(num -> addDomainToUser(user, "domain-" + num));

        Boolean authorized = authorizeUser.isAdminUser(user);

        assertThat(authorized, is(equalTo(false)));
    }


    private User createUser(String... domainNames) {
        User user = new User();
        user.setDomains(new HashSet<>());
        for (String domainName : domainNames) {
            addDomainToUser(user, domainName);
        }
        return user;
    }

    private void addDomainToUser(User user, String domainName) {
        user.getDomains().add(createDomain(domainName));
    }

    private Domain createDomain(String domainName) {
        Domain domain = new Domain();
        domain.setDomainName(domainName);
        return domain;
    }

    @Before
    public void buildUp() {
        authorizeUser = new AuthorizeUser(Collections.emptyList());
    }

    @After
    public void tearDown() {
    }


}
