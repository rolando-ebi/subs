package uk.ac.ebi.subs.repository;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.data.component.Domain;
import uk.ac.ebi.subs.repository.model.Sample;
import uk.ac.ebi.subs.repository.repos.SampleRepository;
import uk.ac.ebi.subs.repository.repos.SubmittablesBulkOperations;

import java.util.Date;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestRepoApplication.class)
public class SubmittablesInDomainTest {

    @Autowired
    private SampleRepository sampleRepository;

    @Autowired
    private SubmittablesBulkOperations submittablesBulkOperations;

    private final String domainName = "testDomain";
    private Domain domain;

    @Before
    public void buildUp() {
        sampleRepository.deleteAll();
        domain = new Domain();
        domain.setName(domainName);
    }

    @Test
    public void testAggregation() {
        sampleRepository.save(sample("bob", "1st"));
        sampleRepository.save(sample("bob", "2nd"));
        sampleRepository.save(sample("bob", "3rd"));
        sampleRepository.save(sample("alice", "1st"));
        sampleRepository.save(sample("charlotte", "1st"));


        Page<Sample> samples = submittablesBulkOperations.itemsByDomain(domainName, new PageRequest(0, 2), Sample.class);

        assertThat(samples,notNullValue());
        assertThat(samples.getTotalElements(),is(equalTo(3L)));
        assertThat(samples.getContent().get(0).getAlias(),equalTo("alice"));// alphabetical ordering works
        assertThat(samples.getContent().get(1).getAlias(),equalTo("bob"));//got bob
        assertThat(samples.getContent().get(1).getTitle(),equalTo("3rd"));//got most recent version of bob
        assertThat(samples.getTotalPages(),is(equalTo(2)));

        samples = submittablesBulkOperations.itemsByDomain(domainName, new PageRequest(1, 2), Sample.class);
        assertThat(samples,notNullValue());
        assertThat(samples.getTotalElements(),is(equalTo(3L)));
        assertThat(samples.getContent().get(0).getAlias(),equalTo("charlotte"));
    }


    private Sample sample(String alias, String title) {
        Sample s = new Sample();
        s.setDomain(domain);
        s.setAlias(alias);

        s.setTitle(title);

        s.setCreatedDate(new Date());

        return s;
    }

}
