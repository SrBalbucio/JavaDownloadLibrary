package javadl.handler;

import javadl.Downloader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Copyright (c) 12.01.2019
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public abstract class DownloadSpeedDownloadHandler extends DownloadHandler {

    private Timer timer;

    public DownloadSpeedDownloadHandler(Downloader downloader) {
        super(downloader);
        timer = new Timer();
    }

    int lastDownloadSize;
    int deltaDownload;

    @Override
    public void onDownloadStart() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                deltaDownload = downloader.getDownloadedBytes() - lastDownloadSize;
                onDownloadTickPerSec(deltaDownload);
                lastDownloadSize = downloader.getDownloadedBytes();
            }
        }, 0, 1000);
    }

    public void onDownloadTickPerSec(int bytesPerSec){};
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
