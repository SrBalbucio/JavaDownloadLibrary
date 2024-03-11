package javadl.utils;

import javadl.handler.CompleteDownloadHandler;
import javadl.handler.DownloadHandler;
import javadl.model.Download;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class StatusPrinter extends CompleteDownloadHandler{

    private Download download;
    private DownloadHandler oldHandler;

    public StatusPrinter(Download download){
        super(download.getInstance());
        this.download = download;
        this.oldHandler = download.getDownloadHandler();
        download.setDownloadHandler(this);
    }

    @Override
    public void onDownloadSpeedProgress(Download download, int downloaded, int maxDownload, int percent, int bytesPerSec) {
        printProgress(download.getStartTime(), maxDownload, downloaded, bytesPerSec);
    }

    @Override
    public synchronized void onDownloadFinish(Download download) {
        super.onDownloadFinish(download);
        download.setDownloadHandler(oldHandler);
    }

    private static void printProgress(long startTime, long total, long current, int veloc) {
        long eta = current == 0 ? 0 :
                (total - current) * (System.currentTimeMillis() - startTime) / current;

        String etaHms = current == 0 ? "N/A" :
                String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
                        TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

        StringBuilder string = new StringBuilder(140);
        int percent = (int) (current * 100 / total);
        string
                .append('\r')
                .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
                .append(String.format(" %d%% [", percent))
                .append(String.join("", Collections.nCopies(percent, "=")))
                .append('>')
                .append(String.join("", Collections.nCopies(100 - percent, " ")))
                .append(']')
                .append(String.join("", Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
                .append(String.format(" %d/%d, %dMB/s ETA: %s", current, total, SizeUtil.toMBFB(veloc), etaHms));

        System.out.print(string);
    }
}
