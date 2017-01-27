package uk.ac.ebi.subs.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.Submission;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadPathResolver {

    @Value("${base-upload-path:.}")
    private String baseUploadPath;

    public Path uploadPath(Submission submission, String fileName){

        return  Paths.get(baseUploadPath, submission.getId(), fileName);

    }
}
