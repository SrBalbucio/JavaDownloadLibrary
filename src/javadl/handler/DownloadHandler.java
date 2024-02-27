package javadl.handler;

import javadl.Downloader;

/**
 * Copyright (c) 12.01.2019
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public abstract class DownloadHandler {

    protected Downloader downloader;

    public DownloadHandler(Downloader downloader) {
        this.downloader = downloader;
    }

    public void onDownloadStart(){};
    public void onDownloadFinish(){};
    public void onDownloadError(Exception e){};
}
