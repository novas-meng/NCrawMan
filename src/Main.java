import NCrawlMan.Downloader.Spider;
import NCrawlMan.UrlCrawal.UrlCheck;
import NCrawlMan.UrlCrawal.UrlCollect;
import NCrawlMan.Utils.BloomFilter;
import NCrawlMan.initConf.torrent;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by novas on 15/11/28.
 */
public class Main
{

    //private  final  String m;
    static ThreadLocal<Integer> threadLocal=new ThreadLocal<>();
    //爬取图片网页
   // http://www.9luyilu.net/yazhousetu/2015-11/12112.html
   static int count=0;
   static Runnable runnable=new Runnable() {
        @Override
        public void run() {
          try
                {
                   threadLocal.set(7);
                    System.out.println(threadLocal.get());
                     //   System.out.println(Thread.currentThread().getName());
                   // Thread.sleep(100);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

    };
    public static void main(String[] args) throws Exception
    {

        torrent torrent=new torrent();
      //  torrent.addRootUrlList("http://www.9luyilu.net/yazhousetu/2015-11/12112.html");
        torrent.addRootUrlList("http://44crcr.com/tupianqu/yazhou/107298.html");
        Spider spider = Spider.getSpiderInstance(torrent);
        spider.start();
        String a="http://www.9luyilu.net";
        System.out.println("...."+BloomFilter.checkExistAndSet(a));
        System.out.println(" ....   "+BloomFilter.checkExistAndSet(a));
        long[] aa=new long[1050000];
        BloomFilter.setArray(4194991,aa);
        System.out.println("over");
      //  System.out.println(BloomFilter.arrayiszero(4194991, aa) + "  "+aa[0]);
        /*
        System.out.println("程序停止");
        String s=new String("dfasf");
        s=UrlCheck.wrapUrl(s);
        System.out.println(s);
        /*
        //http://www.news.cn/info/
        threadLocal.set(8);
        threadLocal.set(9);

        System.out.println(threadLocal.get());
        Thread thread=new Thread(runnable);
        thread.start();

        InputStream is=new FileInputStream("url.txt");
        byte[] bytes=new byte[1024];
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
        //String encode=getEncodingCode(new String(bytes,0,1024));
        UrlCollect urlCollect=UrlCollect.getUrlCollectInstance();

       // urlCollect.collectUrl(new String(bytes),"http://gb.cri.cn/44571/2015/04/14/7872s4931896.htm" );
        urlCollect.collectUrl(new String(bytes),"http://44crcr.com/tupianqu/yazhou/107298.html");
      //  int[] ints=UrlCheck.toBinary(256);
      //  for(int i=0;i<ints.length;i++)
      //  {
      //      System.out.println(ints[i]);
      //  }
        /*
        HashMap<String,Queue<String>> waitingUrlMap=new HashMap<>();
        String a1="a1";
        Queue<String> q1=new LinkedList<>();
        q1.add("fsdf");
        waitingUrlMap.put(a1,q1);

        String a2="a2";
        Queue<String> q2=new LinkedList<>();
        q2.add("fsdf");
        q2.add("fsdf");
        waitingUrlMap.put(a2,q2);

        String a3="a3";
        Queue<String> q3=new LinkedList<>();
        q3.add("fsdf");
        q3.add("fsdf");
        waitingUrlMap.put(a3,q3);

        String a4="a4";
        Queue<String> q4=new LinkedList<>();
        q4.add("fsdf");
        q4.add("fsdf");
        waitingUrlMap.put(a4,q4);


        long m=System.currentTimeMillis();
        for(int j=0;j<1000000;j++)
        {
            Set<Map.Entry<String,Queue<String>>> set=waitingUrlMap.entrySet();
            Iterator<Map.Entry<String,Queue<String>>> iterator=set.iterator();
            String key=null;
            int min=Integer.MAX_VALUE;
            while(iterator.hasNext())
            {
                Map.Entry<String,Queue<String>> entry=iterator.next();
                if(entry.getValue().size()<min)
                {
                    min=entry.getValue().size();
                    key=entry.getKey();
                }
            }
            Queue queue=waitingUrlMap.get(key);
          //  System.out.println(key);
        }

        long n=System.currentTimeMillis();
        System.out.println(n-m);
        */
    }
}
