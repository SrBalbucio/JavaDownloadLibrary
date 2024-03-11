package javadl.handler;

import javadl.Downloader;
import javadl.model.Download;
import lombok.NonNull;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Copyright (c) 12.01.2019
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public abstract class CompleteDownloadHandler extends DownloadHandler {

    private Timer timer;
    private ConcurrentHashMap<Download, TimerTask> tasks = new ConcurrentHashMap<>();

    public CompleteDownloadHandler(@NonNull Downloader downloader) {
        super(downloader);
        timer = new Timer();
    }

    int lastDownloadSize;
    int deltaDownload;
    int percent;

    @Override
    public synchronized void onDownloadStart(Download download) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                deltaDownload = download.getDownloadedBytes() - lastDownloadSize;
                onDownloadTickPerSec(download, deltaDownload);
                lastDownloadSize = download.getDownloadedBytes();
                percent = (int) (((double) download.getDownloadedBytes() / download.getDownloadLength()) * 100);
                onDownloadProgress(download, download.getDownloadedBytes(), download.getDownloadLength(), percent);
                onDownloadSpeedProgress(download, download.getDownloadedBytes(), download.getDownloadLength(), percent, deltaDownload);
            }
        };
        tasks.put(download, task);
        timer.schedule(task, 0, 100);
    }

    public void onDownloadTickPerSec(Download download, int bytesPerSec) {
    }

    public void onDownloadProgress(Download download, int downloaded, int maxDownload, int percent) {
    }

    public void onDownloadSpeedProgress(Download download, int downloaded, int maxDownload, int percent, int bytesPerSec) {
    }

    ;

    public void finish(Download download) {
    }

    ;

    public void error(Download download, Exception e) {
    }

    ;

    @Override
    public synchronized void onDownloadFinish(Download download) {
        tasks.forEach((b, t) -> {
            if (download.equals(b)) {
                t.cancel();
            }
        });
        finish(download);
    }

    @Override
    public void onDownloadError(Download download, Exception e) {
        tasks.forEach((b, t) -> {
            if (download.equals(b)) {
                t.cancel();
            }
        });
        error(download, e);
    }
}
