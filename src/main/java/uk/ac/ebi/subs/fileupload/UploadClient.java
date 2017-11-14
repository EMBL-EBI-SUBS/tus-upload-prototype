package uk.ac.ebi.subs.fileupload;

import io.tus.java.client.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class is a wrapper around {@link TusClient} class.
 */
public class UploadClient {

    private TusClient tusClient;
    private File file;
    private TusUpload upload;
    private TusExecutor executor;

    public UploadClient() throws MalformedURLException {
        this.tusClient = new TusClient();
        tusClient.setUploadCreationURL(new URL("http://localhost:1080/files/"));
        tusClient.enableResuming(new TusURLMemoryStore());
    }

    /**
     * Sets the file to upload and initialize the {@link TusUpload} and {@link UploadExecutor} with it.
     *
     * @param file the {@link File} to upload
     * @throws FileNotFoundException if the file to be uploaded can not be found
     */
    public void setFile(File file) throws FileNotFoundException {
        this.file = file;
        this.upload = new TusUpload(file);
        this.executor = new UploadExecutor(tusClient, upload);
    }

    /**
     * Execute a file upload attempt.
     *
     * @throws ProtocolException, IOException
     */
    public void attemptUpload() throws ProtocolException, IOException {
        executor.makeAttempts();
    }
}
