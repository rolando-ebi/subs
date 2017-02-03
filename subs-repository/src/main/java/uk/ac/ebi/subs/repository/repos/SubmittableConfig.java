package uk.ac.ebi.subs.repository.repos;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.subs.data.submittable.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SubmittableConfig {

    @Bean
    public List<Class> submittablesClassList() {
        return Collections.unmodifiableList(Arrays.asList(
                Analysis.class,
                Assay.class,
                AssayData.class,
                EgaDac.class,
                EgaDacPolicy.class,
                EgaDataset.class,
                Project.class,
                Protocol.class,
                Sample.class,
                SampleGroup.class,
                Study.class
        ));
    }
}
