package vez.utils;

import java.net.URL;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This class implements the Singleton pattern to handle
 * command-line option processing.
 */
public class Options {

    /**
     * Default image names to use for testing.
     */
    private final String[] imageNames = new String[]{
            "ka.png", "uci.png", "dougs_small.jpg", "kitten.png", "schmidt_coursera.jpg", "dark_rider.jpg", "doug.jpg",
            "lil_doug.jpg", "ironbound.jpg", "wm.jpg", "robot.png", "ace_copy.jpg", "tao_copy.jpg", "doug_circle.png"
    };

    /**
     * Prefix for all the URLs.
     */
    private static String URL_PREFIX = "http://www.dre.vanderbilt.edu/~schmidt/gifs/";

    /**
     * The path to the image directory.
     */
    private static final String IMAGE_DIRECTORY_PATH = "DownloadImages";

    private static Options uniqueInstance;

    /**
     * Method to return the one and only singleton uniqueInstance.
     */
    public static Options getInstance() {
        if (uniqueInstance == null ) {
            uniqueInstance = new Options();
        }
        return uniqueInstance;
    }

    /**
     * Return the path for the directory where images are stored.
     */
    public String getDirectoryPath() {
        return IMAGE_DIRECTORY_PATH;
    }

    /**
     * Gets the list of lists of URLs from which the user wants to download images.
     */
    public Map<String, URL> getUrls() {

        // Create a Function that returns a new URL object when applied and converts checked into runtime exception.
        Function<String, URL> urlFactory = ExceptionUtils.rethrowFunction(URL::new);

        return Arrays.stream(imageNames)
                .collect(
                        Collectors.toUnmodifiableMap(
                                Function.identity(), s -> urlFactory.apply( URL_PREFIX + s )
                        )
                );
    }


}
