package NCrawlMan.Downloader;

import NCrawlMan.UrlManager.UrlQueueManage;
import NCrawlMan.Utils.SignalConstants;
import NCrawlMan.Utils.monitor;
import NCrawlMan.initConf.torrent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by novas on 15/11/29.
 */
public class ThreadPool implements monitor
{

    Thread[] threads;
    torrent torrent;
    String[] threadAndUrls;
    UrlQueueManage urlQueueManage;
    private static ThreadPool threadPool;
    public static boolean Apprun=true;
    static ConcurrentHashMap<String,Integer> map=new ConcurrentHashMap<>();
    private ThreadPool(torrent torrent)
    {
        this.torrent=torrent;
        urlQueueManage=UrlQueueManage.getUrlQueueManageInstance();
    }

    public static ThreadPool getThreadPoolInstance(torrent torrent)
    {
        if(threadPool==null)
        {
            threadPool=new ThreadPool(torrent);
        }
        return threadPool;
    }
    public static ThreadPool getThreadPoolInstance()
    {
        if(threadPool==null)
        {
           // throw new Exception("thread must be init by torrent");
        }
        return threadPool;
    }

    public void run()
    {
        for(int i=0;i<threads.length;i++)
        {
            threads[i].start();
        }
    }
    public void provideThreadNamesforUrlManage(String[] names,UrlQueueManage urlQueueManage)
    {
        urlQueueManage.initWaitUrlMap(names);
    }
    public void createThreads()
    {
        threads=new Thread[torrent.threadcount];
        threadAndUrls=new String[torrent.threadcount];
        for(int i=0;i<threads.length;i++)
        {
            threads[i]=new Thread(HttpDownload.getHttpDownloadInstance());
            threads[i].setName("NCrawlMan_"+i);
            map.put(threads[i].getName(),0);
            threadAndUrls[i]=threads[i].getName();
        }
        for(int i=0;i<threadAndUrls.length;i++)
        {
            System.out.println(threadAndUrls[i]);
        }
       provideThreadNamesforUrlManage(threadAndUrls, urlQueueManage);
    }

    @Override
    public void receiveSignal(int signal) {
        System.out.println("停止");
        if(signal== SignalConstants.APP_STOPED)
        {
            for(int i=0;i<threads.length;i++)
            {
               // threads[i].interrupt();
                map.put(threads[i].getName(),1);
            }
            Apprun=false;
            Spider spider=Spider.getSpiderInstance();
           // this.postSignal(spider,);
            spider.receiveSignal(SignalConstants.APP_STOPED);
        }
    }

    @Override
    public void postSignal(monitor monitor, int signal) {

    }
    public void threadInfo()
    {
        for(int i=0;i<threads.length;i++)
        {
            System.out.println(threads[i].getState()+"   "+map.get(threads[i].getName()));
        }
    }
    public void killThread()
    {
        for(int i=0;i<threads.length;i++)
        {
            threads[i].stop();
           // threads[i]=null;
        }
    }
    @Override
    public void monitor() {

    }
}
