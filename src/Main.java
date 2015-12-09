import NCrawlMan.Downloader.Spider;
import NCrawlMan.initConf.torrent;

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
/*
        torrent torrent=new torrent();
        torrent.addRootUrlList("http://www.9luyilu.net/yazhousetu/2015-11/12112.html");
       // torrent.addRootUrlList("http://news.cnhubei.com");
        Spider spider = new Spider(torrent);
        spider.start();
        while (true)
        {

        }
*/

        threadLocal.set(6);
        threadLocal.set(7);
        threadLocal.set(8);
        threadLocal.set(9);

        System.out.println(threadLocal.get());
        Thread thread=new Thread(runnable);
        thread.start();




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
