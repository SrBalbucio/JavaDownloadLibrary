package javadl;

import javadl.handler.DownloadHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;

/**
 * Copyright (c) 12.01.2019
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Downloader {

    @NonNull
    private DownloadHandler downloadHandler = new DownloadHandler(this) {};

    int downloadedBytes;
    int downloadLength;

    public void downloadFileToLocation(String urlStr, File file){
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            downloadLength = con.getContentLength();

            InputStream in = con.getInputStream();
            FileOutputStream out = new FileOutputStream(file);

            byte[] data = new byte[1024];
            int length;

            downloadHandler.onDownloadStart();

            while ((length = in.read(data, 0, 1024)) != -1) {
                out.write(data, 0, length);
                downloadedBytes += length;
            }

            in.close();
            out.close();

            downloadHandler.onDownloadFinish();
        } catch (Exception e) {
            downloadHandler.onDownloadError(e);
        }
    }

    public void downloadFileToLocation(String urlStr, Path path){
        downloadFileToLocation(urlStr, path.toFile());
    }

    public void downloadFileToLocation(String urlStr, String pathToDownload) {
        downloadFileToLocation(urlStr, new File(pathToDownload));
    }

    public Object downloadObject(String urlStr) {

        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            downloadLength = con.getContentLength();

            ObjectInputStream in = new ObjectInputStream(con.getInputStream());

            byte[] data = new byte[1024];
            int length;

            downloadHandler.onDownloadStart();

            while ((length = in.read(data, 0, 1024)) != -1) {
                downloadedBytes += length;
            }

            in.close();

            downloadHandler.onDownloadFinish();
            return in.readObject();
        } catch (Exception e) {
            downloadHandler.onDownloadError(e);
        }
        return null;
    }

}
