package NCrawlMan.Downloader;


import java.net.HttpURLConnection;

/**
 * Created by novas on 15/11/27.
 */
public interface download
{
    public void download();
    public void download(String url,HttpURLConnection httpURLConnection);
}
