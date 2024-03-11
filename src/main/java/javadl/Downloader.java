package javadl;

import javadl.handler.DownloadHandler;
import javadl.model.Download;
import javadl.model.DownloadType;
import lombok.*;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Main class where you must create downloads and manage them.
 *
 * Create it with the custom DownloadHandler to start receiving Download events.
 */
@Data
@RequiredArgsConstructor
public class Downloader {

    @NonNull
    private DownloadHandler downloadHandler = new DownloadHandler(this) {
    };
    private Executor executor = Executors.newCachedThreadPool();

    /**
     * This method downloads the file asynchronously, executing it in a ThreadPool.
     * @param urlStr Download URL
     * @param file Where should the file be saved
     * @return The Download model contains relevant information and methods to assist in handling the process.
     */
    public Download downloadFileToLocation(String urlStr, File file) {
        Download download = new Download(this, DownloadType.FILE, urlStr, file, downloadHandler);
        executor.execute(download);
        return download;
    }

    /**
     * This method downloads the file asynchronously, executing it in a ThreadPool.
     * @param urlStr Download URL
     * @param path Where should the file be saved
     * @return The Download model contains relevant information and methods to assist in handling the process.
     */
    public Download downloadFileToLocation(String urlStr, Path path) {
        return downloadFileToLocation(urlStr, path.toFile());
    }

    /**
     * This method downloads the file asynchronously, executing it in a ThreadPool.
     * @param urlStr Download URL
     * @param pathToDownload Where should the file be saved
     * @return The Download model contains relevant information and methods to assist in handling the process.
     */
    public Download downloadFileToLocation(String urlStr, String pathToDownload) {
        return downloadFileToLocation(urlStr, new File(pathToDownload));
    }

    /**
     * This method downloads a file in the same thread in which it was called, locking it during the process. <br>
     * It can be useful if you want to stop the execution while the Download is not carried out.
     * @param urlStr Download URL
     * @param file Where should the file be saved
     * @return The Download model contains relevant information and methods to assist in handling the process.
     */
    public Download waitForDownloadFileToLocation(String urlStr, File file) {
        Download download = new Download(this, DownloadType.FILE, urlStr, file, downloadHandler);
        download.run();
        return download;
    }

    /**
     * This method downloads a file in the same thread in which it was called, locking it during the process. <br>
     * It can be useful if you want to stop the execution while the Download is not carried out.
     * @param urlStr Download URL
     * @param file Where should the file be saved
     * @return The Download model contains relevant information and methods to assist in handling the process.
     */
    public Download waitForDownloadFileToLocation(String urlStr, Path file) {
        return waitForDownloadFileToLocation(urlStr, file.toFile());
    }

    /**
     * This method downloads a file in the same thread in which it was called, locking it during the process. <br>
     * It can be useful if you want to stop the execution while the Download is not carried out.
     * @param urlStr Download URL
     * @param file Where should the file be saved
     * @return The Download model contains relevant information and methods to assist in handling the process.
     */
    public Download waitForDownloadFileToLocation(String urlStr, String file) {
        return waitForDownloadFileToLocation(urlStr, new File(file));
    }

    public Download downloadObject(String urlStr) {
        Download download = new Download(this, DownloadType.OBJECT, urlStr, new File("downloads"), downloadHandler);
        executor.execute(download);
        return download;
    }

}
