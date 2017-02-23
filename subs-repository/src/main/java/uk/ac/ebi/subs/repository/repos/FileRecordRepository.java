package uk.ac.ebi.subs.repository.repos;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import uk.ac.ebi.subs.data.Submission;
import uk.ac.ebi.subs.data.files.FileRecord;

public interface FileRecordRepository extends MongoRepository<FileRecord, String> {


    FileRecord findBySubmissionIdAndFileName(String submissionId, String fileName);

    Page<FileRecord> findBySubmissionId(String submissionId, Pageable pageable);

}
