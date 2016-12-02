package uk.ac.ebi.subs.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.submittable.Study;


@Configuration
public class ResourceAssemblerConfiguration {

    @Autowired
    private EntityLinks entityLinks;

    @Bean
    ResourceAssembler<Study,Resource<Study>> studyResourceAssembler(){
        return entity -> {
            Resource<Study> studyResource = new Resource<>(entity);


            studyResource.add(entityLinks.linkForSingleResource(entity).withSelfRel());

            return studyResource;
        };
    }
}
