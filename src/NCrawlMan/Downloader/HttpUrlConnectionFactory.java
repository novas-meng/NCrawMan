package NCrawlMan.Downloader;

import java.io.OutputStream;
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
    public static synchronized HttpURLConnection getHttpURLConnection(String Url)
    {
        //模拟浏览器访问，避免有些网站的防止爬虫
        try {
           // URL url=new URL("http://weibo.com?User-Agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
            URL url=new URL(Url);
            String contentType = "Content-Type";
            String contentTypeValue = "application/x-www-form-urlencoded";
           // System.out.println(url.getHost());
            URLConnection urlConnection=url.openConnection();
            httpURLConnection=(HttpURLConnection)urlConnection;
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setInstanceFollowRedirects(true);
          //  httpURLConnection.setRequestMethod("GET");
           // httpURLConnection.
            httpURLConnection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            //httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11") ;
            httpURLConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11) AppleWebKit/601.1.56 (KHTML, like Gecko) Version/9.0 Safari/601.1.56");
         //   httpURLConnection.setRequestProperty("Cache-Control","max-age=0");
        //    httpURLConnection.setRequestProperty(contentType, contentTypeValue);
          //  httpURLConnection.setDoOutput(true);
        //    httpURLConnection.setDoInput(true);
          //  OutputStream os=httpURLConnection.getOutputStream();
          //  os.flush();
            return httpURLConnection;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
