package uk.ac.ebi.subs.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.ebi.subs.data.submittable.Submission;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    SubmissionRepository submissionRepository;

    @Override
    public Page<Submission> fetchSubmissions(Pageable pageable) {
        return submissionRepository.findAll(pageable);
    }

    @Override
    public void storeSubmission(Submission submission){
        submissionRepository.save(submission);
    }

    @Override
    public Submission fetchSubmission(String id) {
        return submissionRepository.findOne(id);
    }

    @Override
    public Page<Submission> fetchSubmissionsByDomainName(Pageable pageable, String domainName) {
        return submissionRepository.findByDomainName(domainName,pageable);
    }
}
