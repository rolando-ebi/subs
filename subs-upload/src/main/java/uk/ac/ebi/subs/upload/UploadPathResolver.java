package uk.ac.ebi.subs.upload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import uk.ac.ebi.subs.data.Submission;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadPathResolver {

    @Value("${uk.ac.ebi.subs.root-upload-path}")
    private String rootUploadPath;

    public Path uploadPath(Submission submission, String fileName){

        return  Paths.get(rootUploadPath, submission.getId(), fileName);

    }
}
