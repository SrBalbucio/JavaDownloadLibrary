
import javadl.Downloader;
import javadl.handler.CompleteDownloadHandler;
import javadl.model.Download;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

public class DownloadTest {

    public static void main(String[] args) throws InterruptedException {
        Downloader downloader = new Downloader();
        Download download =downloader.downloadFileToLocation("https://github.com/SrBalbucio/PaginatedList/releases/download/v1.0/PaginatedList.jar",
                new File("paginatedlist.jar"));
        downloader.setDownloadHandler(new CompleteDownloadHandler(downloader) {

            @Override
            public void onDownloadStart(Download download) {
                super.onDownloadStart(download);
                System.out.println("Downlaod Iniciado!");
            }

            @Override
            public void onDownloadProgress(Download download, int downloaded, int maxDownload, int percent) {
                System.out.println(percent);
            }

            @Override
            public void finish(Download download) {
                super.finish(download);
                System.out.println("Finalizado!");
            }
        });
        while(!download.isFinished()){
            Thread.sleep(1000);
        }
    }

    @Test
    @DisplayName("Try download a file")
    public void downloadFile() throws InterruptedException {
        Downloader downloader = new Downloader();
        downloader.setDownloadHandler(new CompleteDownloadHandler(downloader) {

            @Override
            public void onDownloadStart(Download download) {
                super.onDownloadStart(download);
                System.out.println("Downlaod Iniciado!");
            }

            @Override
            public void onDownloadProgress(Download download, int downloaded, int maxDownload, int percent) {
                System.out.println(percent);
            }

            @Override
            public void finish(Download download) {
                super.finish(download);
                System.out.println("Finalizado!");
            }
        });
        Download download =downloader.downloadFileToLocation("https://github.com/SrBalbucio/PaginatedList/releases/download/v1.0/PaginatedList.jar",
                new File("paginatedlist.jar"));
        while(!download.isFinished()){
            Thread.sleep(1000);
        }
    }
}
