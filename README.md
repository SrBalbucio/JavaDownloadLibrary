[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://srbalbucio.github.io/JavaDownloadLibrary/javadoc/)
# JavaDownloadLibrary
A java library to download files and process the download speed, progress and other things

### Features:

- Download files easy
- Check download speed
- Check download progress
- Directly cast download objects
- Download Text
- Convert file sizes (MB;GB;KB...)


# Getting Started:

### Download File:

```java
Downloader downloader = new Downloader();
Downlaod downlod = downloader.downloadFileToLocation("https://.../file.jar", new File("Downloads", "file.jar"));
```
> Note: You do not need to run the download in a thread or task, as the download is already asynchronous.

### Download to Object
```java
Download downloadedObject = downloader.downloadObject("https://.../file.jar");
Object obj = ownloadedObject.getAsObject();
```

### Add Handler (Check Speed, Progress...):

```
Downloader downloader = new Downloader();
downloader.setDownloadHandler(new CombinedSpeedProgressDownloadHandler(downloader) {
            @Override
            public void onDownloadSpeedProgress(Download download, int downloaded, int maxDownload, int percent, int bytesPerSec) {
                System.out.println(SizeUtil.toMBFB(bytesPerSec)+"/s " + percent + "%");
            }

            @Override
            public void finish(Download download) {
                System.out.println("Download finished");
            }
        });
Download download = downloader.downloadFileToLocation("https://.../file.jar", new File("Downloads", "file.jar"));
```
### Handler

- DownloadHandler (basic information)
- CompleteDownloadHandler (check all informations)

# Convert File sizes

Syntax: SizeUtil.toMBFB() = toMegaBytesFromBytes
        SizeUtil.toGBFB() = toGigiaBytesFromBytes
```
double mb = SizeUtil.toMBFB(2000000000);
double kb = SizeUtil.toKBFB(1000000);
```
