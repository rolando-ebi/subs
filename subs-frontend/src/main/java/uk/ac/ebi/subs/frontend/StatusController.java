package uk.ac.ebi.subs.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.subs.data.status.Status;

import java.util.List;

@RestController
@BasePathAwareController
public class StatusController {

    @Autowired
    private List<Status> releaseStatuses;

    @Autowired
    private List<Status> processingStatuses;


    @RequestMapping("processingStatuses")
    public Page<Status> allProcessingStatus(Pageable pageable) {
        return new PageImpl<Status>(processingStatuses, pageable, processingStatuses.size());
    }

    @RequestMapping("releaseStatuses")
    public Page<Status> allReleaseStatus(Pageable pageable) {
        return new PageImpl<Status>(releaseStatuses, pageable, releaseStatuses.size());
    }

}
