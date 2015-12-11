package NCrawlMan.Downloader;



import NCrawlMan.UrlCrawal.UrlCrawal;
import NCrawlMan.UrlManager.UrlQueueManage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by novas on 15/11/26.
 */
public class HttpDownload  implements download,Runnable
{
    private HttpURLConnection httpURLConnection;
    private UrlQueueManage urlQueueManage;
    private static HttpDownload download;
    private static UrlCrawal crawal;
    private HttpDownload()
    {

    }
    public static HttpDownload getHttpDownloadInstance()
    {
        if(download==null)
        {
            download=new HttpDownload();
        }
        return download;
    }
    /*
    public void provideForCrawal(String url,String content,UrlCrawal urlCrawal)
    {
       urlCrawal.addWaitingHandleContent(new HtmlContent(content,url));
    }
    */
    public static String getEncodingCode(String header)
    {
         String index="charset=".intern();
         int loc=header.indexOf(index);
         int end=header.indexOf('"',loc);
         if(loc==-1)
         {
             return "utf-8";
         }
         return header.substring(loc+index.length(),end);
    }
    @Override
    public  void download(String url, HttpURLConnection httpURLConnection) {
        InputStream is=null;
        byte[] bytes;
        String encode=null;
        try
        {
          //  System.out.println(httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode()==200)
            {
                is=httpURLConnection.getInputStream();
                bytes=new byte[1024];
                byte[] desbytes=new byte[1024];
                int length=0;
                int desloc=0;
                int lastlength=0;
                int k=0;
                while ((length=is.read(bytes))!=-1)
                {
                 //   System.out.println("======"+desloc);
                    System.arraycopy(bytes,0,desbytes,desloc,length);
                    byte[] temp=new byte[desbytes.length+1024];
                    System.arraycopy(desbytes,0,temp,0,desbytes.length);
                    desbytes=temp;
                    k=desloc;
                    desloc=desloc+length;
                    lastlength=length;
                }
                //bytes存放着url内容的二进制表示
                bytes=new byte[k+lastlength];
                System.arraycopy(desbytes, 0, bytes, 0, bytes.length);
              //  encode=getEncodingCode(new String(bytes,0,1024));
                System.out.println("当前处理url="+url);
                crawal=UrlCrawal.getUrlCrawalInstance();
                crawal.addWaitingHandleContent(new HtmlContent(bytes,url));
            }
            else
            {
                System.out.println("url="+url);
                urlQueueManage.addFailUrl(url);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("encode="+encode);
            System.out.println("url="+url);
            urlQueueManage.addFailUrl(url);
        }

    }
//线程启动
    @Override
    public void run() {
         download();
    }

    @Override
    public void download() {
       // return null;
        urlQueueManage=UrlQueueManage.getUrlQueueManageInstance();
       // while(true&&!this.isInterrupted())
        while (ThreadPool.map.get(Thread.currentThread().getName())==0)
        {
           // System.out.println("启动");

            while(urlQueueManage.hasWaitUrl(Thread.currentThread().getName())&&ThreadPool.map.get(Thread.currentThread().getName())==0)
            {
                System.out.println(Thread.currentThread().getName());
                System.out.println("后台进程状态" + UrlCrawal.handler.getState());
                String url=urlQueueManage.provideUrlForHttpDownload(Thread.currentThread().getName());
                System.out.println("down"+url);
                httpURLConnection=HttpUrlConnectionFactory.getHttpURLConnection(url);
                download(url,httpURLConnection);
            }
        }

    }
}
