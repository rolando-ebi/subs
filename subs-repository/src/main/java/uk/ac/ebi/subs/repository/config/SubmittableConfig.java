package uk.ac.ebi.subs.repository.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.ac.ebi.subs.repository.model.*;
import uk.ac.ebi.subs.repository.model.StoredSubmittable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SubmittableConfig {

    @Bean
    public List<Class<? extends StoredSubmittable>> submittablesClassList() {
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
