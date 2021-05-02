package vez.loaders;

import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class ImageLoader {

    private final ExecutorService executor;

    private final List<URL> urls;

    public ImageLoader(ExecutorService executor, List<URL> urls) {
        this.executor = executor;
        this.urls = urls;
    }


}
