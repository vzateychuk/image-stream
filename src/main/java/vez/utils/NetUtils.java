package vez.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Provides some general utility helper methods for network operations.
 */
public final class NetUtils {

    /**
     * A utility class should always define a private constructor.
     */
    private NetUtils() {
    }
    
    /**
     * Download the contents found at the given URL and return them as
     * a raw byte array.
     */
    public static byte[] downloadContent(URL url) {

        System.out.println("downloadContent, Thread: " + Thread.currentThread().getName() + ", url: " + url);

        // The size of the image downloading buffer.
        final int BUFFER_SIZE = 4096;

        // Creates a new ByteArrayOutputStream to write the downloaded
        // contents to a byte array, which is a generic form of the
        // image.
        ByteArrayOutputStream ostream =
            new ByteArrayOutputStream();
        
        // This is the buffer in which the input data will be stored
        byte[] readBuffer = new byte[BUFFER_SIZE];
        int bytes;
        
        // Creates an InputStream from the inputUrl from which to read
    	// the image data.
        try (InputStream istream = url.openStream()) {
            // While there is unread data from the inputStream,
            // continue writing data to the byte array.
            while ((bytes = istream.read(readBuffer)) > 0) 
                ostream.write(readBuffer, 0, bytes);

            return ostream.toByteArray();
        } catch (IOException e) {
            // "Try-with-resources" will clean up the istream
            // automatically.
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns a filename form of the @a url.
     */
    public static String getFileNameForUrl(URL url) {
        // Just use the host and "filename".
        String uriName = url.getHost() + url.getFile();

        // Replace useless characters with UNDERSCORE.
        String fileName = uriName.replaceAll("[./:]", "_");

        // Replace last underscore with a dot
        fileName = fileName.substring(0, fileName.lastIndexOf('_'))
            + "."
            + fileName.substring(fileName.lastIndexOf('_') + 1,
                                 fileName.length());
        return fileName;
    }
}
