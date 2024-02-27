package javadl;

import javadl.handler.DownloadHandler;
import javadl.model.Download;
import javadl.model.DownloadType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 12.01.2019
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
@Data
@RequiredArgsConstructor
public class Downloader {

    @NonNull
    private DownloadHandler downloadHandler = new DownloadHandler(this) {
    };
    private Executor executor = Executors.newCachedThreadPool();

    public Download downloadFileToLocation(String urlStr, File file) {
        Download download = new Download(DownloadType.FILE, urlStr, file, downloadHandler);
        executor.execute(download);
        return download;
    }

    public Download downloadFileToLocation(String urlStr, Path path) {
        return downloadFileToLocation(urlStr, path.toFile());
    }

    public Download downloadFileToLocation(String urlStr, String pathToDownload) {
        return downloadFileToLocation(urlStr, new File(pathToDownload));
    }

    public Download downloadObject(String urlStr) {
        Download download = new Download(DownloadType.OBJECT, urlStr, new File("downloads"), downloadHandler);
        executor.execute(download);
        return download;
    }

}
