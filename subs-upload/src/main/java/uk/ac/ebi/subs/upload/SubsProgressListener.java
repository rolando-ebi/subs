package uk.ac.ebi.subs.upload;

import org.apache.commons.fileupload.ProgressListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.subs.data.Submission;


/**
 * Simple progress listener adapted from here:
 * <p>
 * https://commons.apache.org/proper/commons-fileupload/using.html
 */
public class SubsProgressListener implements ProgressListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Submission submission;

    private long megaBytes = -1;

    public SubsProgressListener(Submission submission){
        this.submission = submission;
    }

    public void update(long pBytesRead, long pContentLength, int pItems) {
        long mBytes = pBytesRead / 1000000;
        if (megaBytes == mBytes) {
            return;
        }
        megaBytes = mBytes;

        if (pContentLength == -1) {
            logger.info("File upload for submission {}, {} bytes have been read.", submission.getId(), pBytesRead);
        } else {
            logger.info("File upload for submission {}, {} of {} bytes have been read.", submission.getId(), pBytesRead, pContentLength);
        }
    }

}
