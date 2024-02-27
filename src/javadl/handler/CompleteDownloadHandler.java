package javadl.handler;

import javadl.Downloader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Copyright (c) 12.01.2019
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public abstract class CompleteDownloadHandler extends DownloadHandler {

    private Timer timer;

    public CompleteDownloadHandler(Downloader downloader) {
        super(downloader);
        timer = new Timer();
    }

    int lastDownloadSize;
    int deltaDownload;
    int percent;

    @Override
    public void onDownloadStart() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                deltaDownload = downloader.getDownloadedBytes() - lastDownloadSize;
                onDownloadTickPerSec(deltaDownload);
                lastDownloadSize = downloader.getDownloadedBytes();
                percent = (int) (((double) downloader.getDownloadedBytes() / downloader.getDownloadLength()) * 100);
                onDownloadProgress(downloader.getDownloadedBytes(), downloader.getDownloadLength(), percent);
                onDownloadSpeedProgress(downloader.getDownloadedBytes(), downloader.getDownloadLength(), percent, deltaDownload);
            }
        }, 0, 1000);
    }

    public void onDownloadTickPerSec(int bytesPerSec) {}

    public void onDownloadProgress(int downloaded, int maxDownload, int percent) {}

    public void onDownloadSpeedProgress(int downloaded, int maxDownload, int percent, int bytesPerSec){};

    public void finish(){};
    public void error(Exception e){};

    @Override
    public void onDownloadFinish() {
        timer.cancel();
        finish();
    }

    @Override
    public void onDownloadError(Exception e) {
        timer.cancel();
        error(e);
    }
}
