package NCrawlMan.Downloader;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by novas on 15/11/26.
 */
public class HttpUrlConnectionFactory {
    private HttpUrlConnectionFactory()
    {

    }
    private static HttpURLConnection httpURLConnection;
    public static HttpURLConnection getHttpURLConnection(String Url)
    {
        //模拟浏览器访问，避免有些网站的防止爬虫
        try {
            URL url=new URL(Url);
           // url.getHost();
            System.out.println(url.getHost());
            URLConnection urlConnection=url.openConnection();
            httpURLConnection=(HttpURLConnection)urlConnection;
           // httpURLConnection.
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11") ;
            return httpURLConnection;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
