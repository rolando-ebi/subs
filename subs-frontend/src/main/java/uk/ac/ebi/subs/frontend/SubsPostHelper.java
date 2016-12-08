package uk.ac.ebi.subs.frontend;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public class SubsPostHelper {

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
    public static ResponseEntity<Void> postCreatedResponse(Resource resource){

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

}
