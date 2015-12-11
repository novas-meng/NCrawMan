package NCrawlMan.UrlCrawal;

import NCrawlMan.UrlManager.UrlQueueManage;
import NCrawlMan.Utils.BloomFilter;
import NCrawlMan.Utils.TorrentConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.Serializable;
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
    //存储要爬取网页url的后缀
    private ArrayList<String> htmlsuffixlist=new ArrayList<>();
    private ArrayList<String> imgsuffixlist=new ArrayList<>();

    /*

   网页的结束形式可以是html，htm，或者直接/ 这样的形式
     */
    private UrlCollect()
    {
        htmlsuffixlist.add(".html");
        htmlsuffixlist.add(".htm");
        htmlsuffixlist.add("/");
        imgsuffixlist.add(".jpg");
        imgsuffixlist.add(".png");
        imgsuffixlist.add(".gif");

        try
        {
           // fileWriter=new FileWriter("testurl.txt");
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
    public  String getUrlFromBaseUrl(String href,String baseurl,ArrayList<String> suffix)
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
      //  else if(href.endsWith(".html"))
        else
        {
          //  for(int i=0;i<suffix.size();i++)
          //  {
          //      if(href.endsWith(suffix.get(i)))
           //     {
                    resurl=removedir(baseurl,0)+File.separatorChar+href;
                    return resurl;
             //   }
          //  }

        }
      //  return null;
    }
    //获取所有的url，分为两个部分，
    public void collectUrl(String content,String baseurl)
    {
         if(TorrentConstants.ALLOWED_IMAGE_DOWNLOADED)
         {
             collectHtmlUrl("src=",imgsuffixlist,content,baseurl);
         }
        collectHtmlUrl("href=", htmlsuffixlist, content, baseurl);
    }
    //根据内容和url去获取内容中所有的url。prefix表示前缀，suffix表示后缀，后缀可以是多种形式,所以应该采用list
    //比如可能有以下几种情况href=www,href="www  href='www
    public  void collectHtmlUrl(String prefix,ArrayList<String> suffix,String content,String baseurl)
    {
        int i=0;
        int m=0;
       // String beginstr="href=\"".intern();
        String beginstr=prefix;
      //  String endstr1="\"".intern();
       // String endstr2=">".intern();
    //    String endstr3=" ".intern();
       // System.out.println(content.length());
        while((m=content.indexOf(beginstr,i))>0)
        {
           // System.out.println("m="+m);
            char endchar=content.charAt(m+beginstr.length());
            int c;
            if(endchar!='\''&&endchar!='"')
            {
               // endchar
                int a=content.indexOf('>',m+beginstr.length()+1);
                int b=content.indexOf(' ',m+beginstr.length()+1);
                if(a==-1)
                {
                    a=Integer.MAX_VALUE;
                }
                if(b==-1)
                {
                    b=Integer.MAX_VALUE;
                }
                c=a<b?a:b;
            }
            else
            {
                c=content.indexOf(endchar,m+beginstr.length()+1);
            }
            /*
            int a=content.indexOf(endstr1,m+beginstr.length()+1);
            int b=content.indexOf(endstr2,m+beginstr.length()+1);
            int c=content.indexOf(endstr3,m+beginstr.length()+1);
            if(a==-1)
            {
                a=Integer.MAX_VALUE;
            }
            if(b==-1)
            {
                b=Integer.MAX_VALUE;
            }
            if(c==-1)
            {
                c=Integer.MAX_VALUE;
            }
            */
          //  int d=a<b?a:b;
         //   int e=c<d?c:d;
         //   c=e;
            String url=null;
            url=content.substring(m+beginstr.length(),c);
           // System.out.println("url="+url);
            if(url.charAt(0)=='"'||url.charAt(0)=='\'')
            {
                url=url.substring(1);
            }
            if(url.length()>=4&&url.charAt(0)=='h'&&url.charAt(1)=='t'&&url.charAt(2)=='t'&&url.charAt(3)=='p')
            {
                for(int k=0;k<suffix.size();k++)
                {
                    if(url.endsWith(suffix.get(k)))
                    {
                       // System.out.println("url="+url);
                        set.add(url);
                        break;
                    }
                }
            }
            else if(url.length()>=2)
            {
                String resurl=getUrlFromBaseUrl(url,baseurl,suffix);
                if(resurl!=null)
                {
                    for(int k=0;k<suffix.size();k++)
                    {
                        if(resurl.endsWith(suffix.get(k)))
                        {
                          //  System.out.println("url="+UrlCheck.wrapUrl(resurl));
                            set.add(UrlCheck.wrapUrl(resurl));
                        }
                    }
                }
            }
            i=c;
        }

     // provideUrlForWaitingQueue(set);
        urlSort(set,baseurl);
    }
    /*
    在从网页中提取的众多url中，这些url应该按照与baseurl的相似度进行排序，先抓取相似的url；
    比如baseurl 是http://www.9luyilu.net/yazhousetu/2015-11/12112.html
    那么从内容中提取的url中，http://www.9luyilu.net/yazhousetu/2015-11/12114.html 的相关性要比
    http://www.9luyilu.net/gudianwuxia/ 更强一些
    如何判断相似性，使用simhash，但是发现simhash对于url这种需要最大前缀匹配的效果不是很好，所以算法在UrlCheck中
    */
    public void  urlSort(Set<String> stringSet,String baseurl)
    {
        List<String> urllist=UrlCheck.urlSort(stringSet, baseurl);
        provideUrlForWaitingQueue(urllist);
    }
    //使用布隆过滤器进行过滤
    public  void provideUrlForWaitingQueue(List<String> set)
    {
        Iterator<String> stringIterator=set.iterator();
        while(stringIterator.hasNext())
        {
            String url=stringIterator.next();
           // System.out.println(url);
            //筛选条件:布隆过滤器不存在 而且url满足规则
            if(!BloomFilter.checkExistAndSet(url))
            {

                /*
                try
                {
                    fileWriter.write(url+"\n");
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
*/
               // System.out.println("添加url="+url);
                urlQueueManage.addWaitingUrl(url);
            }
        }
        set.clear();
    }
}
