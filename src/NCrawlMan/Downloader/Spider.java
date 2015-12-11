package NCrawlMan.Downloader;

import NCrawlMan.UrlCrawal.UrlCrawal;
import NCrawlMan.UrlManager.UrlQueueManage;
import NCrawlMan.Utils.SignalConstants;
import NCrawlMan.Utils.monitor;
import NCrawlMan.initConf.torrent;

/**
 * Created by novas on 15/11/27.
 */
public class Spider implements monitor
{
    /*
       线程停止方法采用wait 和 notify的方式；
       同时后台线程中采用join切换
     */
    torrent torrent;
    UrlQueueManage manage;
    ThreadPool threadPool;
    boolean appRun=true;
    private static Spider spider;
    public static Thread mainThread;
    Object lock=new Object();
    private Spider(torrent torrent)
    {
        mainThread=Thread.currentThread();
        this.torrent=torrent;
        init();
    }
    public static Spider getSpiderInstance()
    {
        if(spider==null)
        {
            return null;
        }
        return spider;
    }
    public static Spider getSpiderInstance(torrent torrent)
    {
        if(torrent!=null)
        {
            if(spider==null)
            {
                spider=new Spider(torrent);
            }
            return spider;
        }
        return null;
    }
    //先是创建ThreadPool的多个线程，然后将种子链接加入链接管理中，接下来让链接管理负责分发
    public void init()
    {
        manage=UrlQueueManage.getUrlQueueManageInstance();
      //  httpDownload=HttpDownload.getHttpDownloadInstance();
        threadPool=ThreadPool.getThreadPoolInstance(torrent);
        threadPool.createThreads();
        manage.addWaitingUrl(torrent.getRootUrlList());
    }
    public void start()
    {

        threadPool.run();
        synchronized (lock)
        {
            try
            {
                lock.wait();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
       // System.out.println("运行........");
       // System.out.println(Thread.currentThread().getName()+"  "+ThreadPool.Apprun);

    }

    @Override
    public void postSignal(monitor monitor, int signal) {

    }

    @Override
    public void monitor() {

    }

    @Override
    public void receiveSignal(int signal) {
     //   System.out.println(mainThread.getName());
     ////   System.out.println("接收到停止");
     //   System.out.println(UrlCrawal.handler.getState());
     //   threadPool.threadInfo();
   //     threadPool.killThread();
      //  threadPool.threadInfo();
   //    System.out.println(Thread.activeCount());
    //    System.out.println(Thread.currentThread().getName()+"  "+ThreadPool.Apprun);
     //   System.out.println(Thread.currentThread().getName()+"  "+ThreadPool.Apprun);
        if(signal== SignalConstants.APP_STOPED)
        {
           // System.out.println("运行");
            //appRun=false;
          //  start();
            synchronized (lock)
            {
                lock.notify();
            }
        }
    }
}
