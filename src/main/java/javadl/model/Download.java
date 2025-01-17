package javadl.model;

import javadl.Downloader;
import javadl.handler.DownloadHandler;
import javadl.utils.StatusPrinter;
import lombok.*;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

@RequiredArgsConstructor
@Data
public class Download implements Runnable {

    private long startTime = System.currentTimeMillis();
    @NonNull
    @Getter
    private Downloader instance;
    @Getter
    @Setter
    private String id = "Download Anonymous";
    private int downloadedBytes;
    private int downloadLength;
    @NonNull
    private DownloadType type;
    @NonNull
    private String urlStr;
    @NonNull
    private File file;
    @NonNull
    private DownloadHandler downloadHandler;
    private Object downloadObject;
    private boolean finished;

    public Object getAsObject() {
        return downloadObject;
    }

    /**
     * Get StatusPrinter that will automatically print your download status to your console!
     * @return StatusPrinter
     */
    public StatusPrinter printer(){
        return new StatusPrinter(this);
    }

    @Override
    public void run() {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            downloadLength = con.getContentLength();

            InputStream in = con.getInputStream();
            OutputStream out = null;

            if (type == DownloadType.FILE) {
                out = Files.newOutputStream(file.toPath());
            } else {
                in = new ObjectInputStream(con.getInputStream());
            }

            byte[] data = new byte[1024];
            int length;

            downloadHandler.onDownloadStart(this);

            while ((length = in.read(data, 0, 1024)) != -1) {
                if (type == DownloadType.FILE) out.write(data, 0, length);
                downloadedBytes += length;
            }

            if (type == DownloadType.OBJECT)
                downloadObject = ((ObjectInputStream) in).readObject();

            out.flush();
            in.close();
            out.close();

            downloadHandler.onDownloadFinish(this);
            finished = true;
        } catch (Exception e) {
            downloadHandler.onDownloadError(this, e);
        }
    }
}
