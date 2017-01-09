package uk.ac.ebi.subs.frontend.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class SubsResponseHelper {

    @Autowired
    private RepositoryEntityLinks repositoryEntityLinks;

    /**
     * Where a POST request is used to create a resource,
     * respond with HttpStatus.CREATED and a location.
     *
     * Construct the location using the self link from a resource.
     *
     * Do not include the Resource in the response body
     *
     * @param resource
     * @return
     */
    public ResponseEntity<Void> postCreatedResponse(Resource resource){

        HttpHeaders headers = new HttpHeaders();
        Link selfLink = resource.getLink("self");

        if (selfLink != null) {
            String linkHref = selfLink.getHref();
            URI locHref = URI.create(linkHref);
            headers.setLocation(locHref);
        }

        ResponseEntity<Void> responseEntity = new ResponseEntity(headers, HttpStatus.CREATED);

        return responseEntity;
    }

    /**
     * Where a POST request is used to create a resource,
     * respond with HttpStatus.CREATED and a location.
     *
     * Construct the location using the self link from a resource.
     *
     * Do not include the Resource in the response body
     *
     * @param identifiable
     * @return
     */
    public ResponseEntity<Void> postCreatedResponse(Identifiable identifiable){
        HttpHeaders headers = new HttpHeaders();
        Link selfLink =  repositoryEntityLinks.linkToSingleResource(identifiable);

        if (selfLink != null) {
            String linkHref = selfLink.getHref();
            URI locHref = URI.create(linkHref);
            headers.setLocation(locHref);
        }

        ResponseEntity<Void> responseEntity = new ResponseEntity(headers, HttpStatus.CREATED);

        return responseEntity;
    }

    /**
     * Where a PUT request is used to create a resource,
     * respond with HttpStatus.OK
     *
     * HttpStatus.NO_CONTENT is also reasonable, but seems less clear to consumers
     *
     * @return
     */
    public ResponseEntity<Void> putUpdatedResponse(){

        return new ResponseEntity(HttpStatus.OK);

    }

    /**
     * Where a DELETE request is used to remove a resource,
     * respond with HttpStatus.OK
     *
     * HttpStatus.NO_CONTENT is also reasonable, but seems less clear to consumers
     *
     * @return
     */
    public ResponseEntity<Void> deleteRemovedResponse(){

        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
