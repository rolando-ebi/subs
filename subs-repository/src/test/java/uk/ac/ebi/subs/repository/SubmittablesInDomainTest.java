package uk.ac.ebi.subs.repository;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ebi.subs.data.component.Domain;
import uk.ac.ebi.subs.repository.model.Sample;
import uk.ac.ebi.subs.repository.repos.submittables.SampleRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestRepoApplication.class)
public class SubmittablesInDomainTest {

    @Autowired
    private SampleRepository sampleRepository;

    private final String domainName = "testDomain";
    private Domain domain;

    private final Logger logger = LoggerFactory.getLogger(SubmittablesInDomainTest.class);


    @Before
    public void buildUp() {
        sampleRepository.deleteAll();
        domain = new Domain();
        domain.setName(domainName);
    }

    @Test
    public void testAggregationWithNoData() {
        Page<Sample> samples = sampleRepository.submittablesInDomain(domainName, new PageRequest(0, 100));
        assertThat(samples, notNullValue());
        assertThat(samples, emptyIterable());
        assertThat(samples.getTotalElements(), is(equalTo(0L)));
    }

    @Test
    public void testAggregation() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");


        sampleRepository.save(sample("bob", "1st", sdf.parse("2000-01-01")));
        sampleRepository.save(sample("bob", "2nd", sdf.parse("2000-01-02")));
        sampleRepository.save(sample("bob", "3rd", sdf.parse("2000-01-03")));
        sampleRepository.save(sample("alice", "1st"));
        sampleRepository.save(sample("charlotte", "1st"));


        Page<Sample> samples = sampleRepository.submittablesInDomain(domainName, new PageRequest(0, 2));

        assertThat(samples, notNullValue());
        assertThat(samples.getTotalElements(), is(equalTo(3L)));
        assertThat(samples.getContent().get(0).getAlias(), equalTo("alice"));// alphabetical ordering works
        assertThat(samples.getContent().get(0).getTitle(), equalTo("1st"));// alphabetical ordering works
        assertThat(samples.getContent().get(1).getAlias(), equalTo("bob"));//got bob
        assertThat(samples.getContent().get(1).getTitle(), equalTo("3rd"));//got most recent version of bob
        assertThat(samples.getTotalPages(), is(equalTo(2)));

        samples = sampleRepository.submittablesInDomain(domainName, new PageRequest(1, 2));
        assertThat(samples, notNullValue());
        assertThat(samples.getTotalElements(), is(equalTo(3L)));
        assertThat(samples.getContent().get(0).getAlias(), equalTo("charlotte"));

    }


    private Sample sample(String alias, String title) {
        return sample(alias, title, new Date());
    }

    private Sample sample(String alias, String title, Date createdDate) {
        Sample s = new Sample();
        s.setDomain(domain);
        s.setAlias(alias);

        s.setTitle(title);

        s.setCreatedDate(createdDate);

        return s;
    }

}
