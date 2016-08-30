package uk.ac.ebi.subs.validation.checklist;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.xmlbeans.XmlException;

import java.io.IOException;
import java.io.InputStream;

public class FTPChecklistValidatorFactoryImpl extends ChecklistValidatorFactoryImpl {
    public static final String CHECKLIST_FTP_SERVER = "ftp.sra.ebi.ac.uk";
    public static final String META_XML_CHECKLIST_XML = "meta/xml/checklist.xml";
    public static final String USERNAME = "anonymous";
    public static final String PASSWORD = "subs-dev@ebi.ac.uk";

    public FTPChecklistValidatorFactoryImpl() throws IOException, XmlException {
        this(CHECKLIST_FTP_SERVER, META_XML_CHECKLIST_XML);
    }

    public FTPChecklistValidatorFactoryImpl(String ftpServerName, String checklistRemoteFilePath) throws IOException, XmlException {

        super(getFTPInputStream(ftpServerName, checklistRemoteFilePath));
    }

    private static InputStream getFTPInputStream(String ftpServerName, String checklistRemoteFilePath) throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(ftpServerName);
        int reply = ftp.getReplyCode();

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("FTP server " + ftpServerName + " refused connection");
        }

        if (!ftp.login(USERNAME, PASSWORD)) {
            ftp.logout();
            throw new IOException("FTP server failed to login using username anonymous");
        }
        ftp.setFileType(FTP.ASCII_FILE_TYPE);
        InputStream inputStream = ftp.retrieveFileStream(checklistRemoteFilePath);
        return inputStream;
    }
}
