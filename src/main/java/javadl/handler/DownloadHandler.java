package javadl.handler;

import javadl.Downloader;
import javadl.model.Download;
import lombok.NonNull;

/**
 * Copyright (c) 12.01.2019
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public abstract class DownloadHandler {

    protected Downloader downloader;

    public DownloadHandler(@NonNull Downloader downloader) {
        this.downloader = downloader;
    }

    public void onDownloadStart(Download download){};
    public void onDownloadFinish(Download download){};
    public void onDownloadError(Download downlaod, Exception e){};
}
