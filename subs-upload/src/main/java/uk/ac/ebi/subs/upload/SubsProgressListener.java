package uk.ac.ebi.subs.upload;

import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.subs.data.Submission;

import java.util.Date;


/**
 * Simple progress listener adapted from here:
 * <p>
 * https://commons.apache.org/proper/commons-fileupload/using.html
 */
public class SubsProgressListener implements ProgressListener {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Submission submission;

    private int maximumUpdatePeriodInSeconds = 60;
    private long minimumUpdateSizeInBytes = FileUtils.ONE_MB * 100;

    private Date lastTime;
    private long lastBytesRead = -1;

    public SubsProgressListener(Submission submission) {
        this.submission = submission;
        lastTime = new Date();
    }

    public void update(long pBytesRead, long pContentLength, int pItems) {

        if (!logger.isInfoEnabled()) {
            return;
        }

        Date updateTime = new Date();

        long chunksRead = pBytesRead / minimumUpdateSizeInBytes;
        long lastChunksRead = lastBytesRead / minimumUpdateSizeInBytes;

        float secondsElapsed = (float) (updateTime.getTime() - lastTime.getTime()) / 1000;

        if (chunksRead == lastChunksRead && secondsElapsed < maximumUpdatePeriodInSeconds ) {
            //don't update too often, wait for another full 'chunk' or the maximum update period to elapse
            return;
        }

        long uploadedBytesPerSecond = -1;
        if (lastBytesRead > -1) {
            uploadedBytesPerSecond = (long) ((pBytesRead - lastBytesRead) / secondsElapsed);
        }


        lastBytesRead = pBytesRead;
        lastTime = updateTime;


        if (pContentLength == -1) {
            logger.info("Upload for submission {}, {} have been read",
                    submission.getId(),
                    FileUtils.byteCountToDisplaySize(pBytesRead));
        } else {
            logger.info("File upload for submission {}, {} of {} have been read",
                    submission.getId(),
                    FileUtils.byteCountToDisplaySize(pBytesRead),
                    FileUtils.byteCountToDisplaySize(pContentLength)
            );
        }

        if (uploadedBytesPerSecond > -1) {
            logger.info("File upload for submission {} current rate is {} per second.",
                    submission.getId(),
                    FileUtils.byteCountToDisplaySize(uploadedBytesPerSecond)
            );

        }
    }

}
