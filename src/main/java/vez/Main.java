package vez;

import vez.utils.*;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class Main {

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        // define list of URLs
        Map<String, URL> urls = Options.getInstance().getUrls();
        // Run async image loader
        downloadImagesAsync(urls);
        // shutdown the executor
        executor.shutdown();

        ExceptionUtils.rethrowConsumer((Integer i) -> executor.awaitTermination(i, TimeUnit.MINUTES)).accept(10);

    }

    private static void downloadImagesAsync(Map<String, URL> urlMap) {
        System.out.println("Starting downloadImagesAsync");
        long startTime = System.currentTimeMillis();

        urlMap.keySet().stream()
                .filter(Predicate.not(Main::isLoaded)) // checks if a URL is already loaded.
                .map(urlMap::get)

                // Use map() to call downloadImageAsync(), which transforms each non-cached URL to a future to an image
                // (i.e., asynchronously download each image via its URL).
                .map(Main::downloadImageAsync)

                // Trigger intermediate processing and create a future that can be used to wait for all operations associated
                // with the stream of futures to complete.
                .collect(FuturesCollector.toFuture())

                // thenAccept() is called when all the futures in the stream complete their asynchronous processing.
                .thenAccept( list -> {
                    list.forEach(myImage -> System.out.println(myImage.getFileName()));
                    System.out.println("Total: " + list.size());
                } )

                // Wait until all the images have been downloaded, processed, and stored.
                .join();


        long finishTime = System.currentTimeMillis();
        System.out.println("Ending downloadImagesAsync, time: " + (finishTime - startTime));
    }


    /**
     * Asynchronously download an image from the {@code url} parameter.
     *
     * @param url the URL to download
     * @return A future that completes when the image finishes downloading
     */
    private static CompletableFuture<MyImage> downloadImageAsync(URL url) {
        // Return a future that completes when the image finishes downloading.
        return CompletableFuture.supplyAsync(
                () -> new MyImage(url, NetUtils.downloadContent(url) ),
                executor
        );
    }

    private static boolean isLoaded(String s) {
        return false;
    }
}
