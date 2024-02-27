package javadl.handler;

import javadl.Downloader;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Copyright (c) 12.01.2019
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public abstract class DownloadProgressDownloadHandler extends DownloadHandler {

    private Timer timer;

    public DownloadProgressDownloadHandler(Downloader downloader) {
        super(downloader);
        timer = new Timer();
    }


    @Override
    public void onDownloadStart() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onDownloadProgress(downloader.getDownloadedBytes(), downloader.getDownloadLength(), (int) (((double) downloader.getDownloadedBytes() / downloader.getDownloadLength()) * 100));
            }
        }, 0, 1000);
    }

    public void onDownloadProgress(int downloaded, int maxDownload, int percent){};
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
