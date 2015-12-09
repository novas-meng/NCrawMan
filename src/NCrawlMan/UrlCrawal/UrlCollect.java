package NCrawlMan.UrlCrawal;

import NCrawlMan.UrlManager.UrlQueueManage;
import NCrawlMan.Utils.BloomFilter;

import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by novas on 15/11/28.
 *
 *
 *这个类主要是负责从网页中抓取url，同时利用布隆过滤器进行重复url的过滤
 */
public class UrlCollect
{
    private UrlQueueManage urlQueueManage=UrlQueueManage.getUrlQueueManageInstance();
    FileWriter fileWriter;
    static Set<String> set=new TreeSet<String>();
    //单例模式，生成对象
    private static UrlCollect urlCollect;
    private UrlCollect()
    {
        try
        {
            fileWriter=new FileWriter("url.txt");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static UrlCollect getUrlCollectInstance()
    {
        if(urlCollect==null)
        {
            urlCollect=new UrlCollect();
        }
        return urlCollect;
    }
    //将搜索的url，分解成目录的形式，方便处理./,../或者直接文件名这样的连接
    public  String removedir(String baseurl,int depth)
    {
        int[] indexs=new int[20];
        int length=0;
        int loc=0;
        while((loc=baseurl.indexOf(File.separatorChar,loc))!=-1)
        {
            indexs[length]=loc;
            loc++;
            length++;
        }
        return baseurl.substring(0,indexs[length-1-depth]);
    }
    public  String getUrlFromBaseUrl(String href,String baseurl)
    {
        String host=null;
        String resurl;
        //提取主机地址
        try {
            URL url=new URL(baseurl);
            host=url.getHost();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        char[] array=href.toCharArray();
        //处理 \ 这样的情况，表示根目录
        if(array[0]==File.separatorChar)
        {
            resurl=host+href;
            return resurl;
        }
        //处理 .\  这样的情况，表示当前目录
        else if(array[0]=='.'&&array[1]==File.separatorChar)
        {
            resurl=removedir(baseurl,0)+href.substring(1);
            return resurl;
        }
        //处理 ..\  这样的情况，表示上一目录
        else if(array[0]=='.'&&array[1]=='.'&&array[2]==File.separatorChar)
        {
            int count=3;
            while(count<array.length&&count+1<array.length&&count+2<array.length&&array[0+count]=='.'&&array[1+count]=='.'&&array[2+count]==File.separatorChar)
            {
                count=count+3;
            }
            resurl=removedir(baseurl,count/3)+href.substring(count-1);
            return resurl;
        }
        //处理只有文件名的情况
        else if(href.endsWith(".html"))
        {
            resurl=removedir(baseurl,0)+File.separatorChar+href;
            return resurl;
        }
        return null;
    }

    public  void collect(String content,String baseurl)
    {
        int i=0;
        int m=0;
        String beginstr="href=\"".intern();
        String endstr1="\"".intern();
        String endstr2=">".intern();
       // System.out.println(content.length());
        while((m=content.indexOf(beginstr,i))>0)
        {
            int a=content.indexOf(endstr1,m+beginstr.length());
            int b=content.indexOf(endstr2,m+beginstr.length());
            if(a==-1)
            {
                a=Integer.MAX_VALUE;
            }
            if(b==-1)
            {
                b=Integer.MAX_VALUE;
            }
            int c=a<b?a:b;
            String url=null;
            url=content.substring(m+beginstr.length(),c);
            if(url.length()>=4&&url.charAt(0)=='h'&&url.charAt(1)=='t'&&url.charAt(2)=='t'&&url.charAt(3)=='p')
            {
                set.add(url);
            }
            else if(url.length()>=2)
            {
                String resurl=getUrlFromBaseUrl(url,baseurl);
                if(resurl!=null)
                {
                    set.add(resurl);
                }
            }
            i=c;
        }
      provideUrlForWaitingQueue(set);
    }
    //使用布隆过滤器进行过滤
    public  void provideUrlForWaitingQueue(Set<String> set)
    {
        Iterator<String> stringIterator=set.iterator();
        while(stringIterator.hasNext())
        {
            String url=stringIterator.next();
            if(!BloomFilter.checkExistAndSet(url))
            {
                // System.out.println("增加"+url);
                try
                {
                    fileWriter.write(url+"\n");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                urlQueueManage.addWaitingUrl(url);
            }
        }
        set.clear();
    }
}
