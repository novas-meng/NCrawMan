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
public class HttpDownload  extends Thread implements download
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
    public void provideForCrawal(String url,String content,UrlCrawal urlCrawal)
    {
       urlCrawal.addWaitingHandleContent(new HtmlContent(content,url));
    }
    public static String getEncodingCode(String header)
    {
         String index="charset=".intern();
         int loc=header.indexOf(index);
         int end=header.indexOf('"',loc);
         return header.substring(loc+index.length(),end);
    }
    @Override
    public  void download(String url, HttpURLConnection httpURLConnection) {
        //return null;
       //  System.out.println("开始下载") ;
        InputStream is=null;
        byte[] bytes;
        try
        {
            System.out.println(httpURLConnection.getResponseCode());
            if(httpURLConnection.getResponseCode()==200)
            {
                is=httpURLConnection.getInputStream();
                /*
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                FileWriter fw=new FileWriter("novastemp.html");
                String line=br.readLine();
                while(line!=null)
                {
                    fw.write(line);
                    line=br.readLine();
                }
                */
             //   System.out.println("over");
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
                bytes=new byte[k+lastlength];

                System.arraycopy(desbytes, 0, bytes, 0, bytes.length);

                String encode=getEncodingCode(new String(bytes,0,1024));
            //    String encode= httpURLConnection.getContentType();
            //    System.out.println("encode="+encode);
            //    int loc=encode.indexOf("=");
            //    encode=encode.substring(loc + 1);
              //  System.out.println("encode="+encode);
                FileWriter fw=new FileWriter("novastemp.html");
                fw.write(new String(bytes,encode));
                fw.close();
                crawal=UrlCrawal.getUrlCrawalInstance();
               // System.out.println("成功"+new String(bytes));

             //   provideForCrawal(url, new String(bytes, encode), crawal);
            }
            else
            {
                urlQueueManage.addFailUrl(url);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
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
        while(true&&this.isInterrupted())
        {
            while(urlQueueManage.hasWaitUrl(Thread.currentThread().getName()))
            {
                String url=urlQueueManage.provideUrlForHttpDownload(Thread.currentThread().getName());
                System.out.println("down"+url);
                httpURLConnection=HttpUrlConnectionFactory.getHttpURLConnection(url);
                download(url,httpURLConnection);
            }
        }

    }
}
