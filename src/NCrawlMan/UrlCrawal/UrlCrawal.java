package NCrawlMan.UrlCrawal;

import NCrawlMan.Downloader.HtmlContent;
import NCrawlMan.Downloader.Spider;
import NCrawlMan.Downloader.ThreadPool;
import NCrawlMan.UrlManager.UrlQueueManage;
import NCrawlMan.Utils.ContentSave;
import NCrawlMan.Utils.TorrentConstants;

import java.io.FileWriter;
import java.lang.ref.ReferenceQueue;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by novas on 15/11/27.
 */
/*
@author novas
这个类的作用是用来进行Url爬取和数据保存的。

 */
public class UrlCrawal {
    private static Object lock=new Object();
    private static UrlQueueManage urlQueueManage;
    private static UrlCollect urlCollect;
    private static ContentSave contentSave;
    public static Thread handler;
    //后台进程，负责从获取到的内容中提取出url,然后将内容保存到文件
    private static class QueueHandler  extends Thread
    {
        public QueueHandler(ThreadGroup group,String name)
        {
            super(group, name);
        }
        @Override
        public void run() {
            System.out.println("后台进程启动");
            while (ThreadPool.Apprun)
            {
                synchronized (lock)
                {
                    if(!waitingforHandleQueue.isEmpty())
                    {
                        System.out.println("=======");
                        HtmlContent htmlcontent=waitingforHandleQueue.poll();
                        htmlcontent.parse();
                        if(htmlcontent.type== TorrentConstants.FILE_TYPE_TEXT)
                        {
                            urlCollect.collectUrl(htmlcontent.content, htmlcontent.url);
                           // contentSave.saveTextToFile(htmlcontent.url,htmlcontent.content);
                        }
                        else
                        {
                            contentSave.saveImageToFile(htmlcontent.url,htmlcontent.bytes);
                        }
                    }
                }
            }

          //  System.out.println("后台进程结束");
          //  this.setPriority(Thread.MIN_PRIORITY);

         //   Thread.yield();
            try {
                Spider.mainThread.join();
              //  System.out.println("后台进程结束");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
//
           // this.setDaemon(false);
        }

    }
    static
    {
        ThreadGroup group=Thread.currentThread().getThreadGroup();
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        for (ThreadGroup tgn = tg;
             tgn != null;
             tg = tgn, tgn = tg.getParent());
        handler=new QueueHandler(group,"QueueHandler");
       // handler=new Thread(new QueueHandler());
       // handler.setName("sdfasdf");
        handler.setPriority(Thread.MAX_PRIORITY);
        handler.setDaemon(true);
        handler.start();
    }
    private static UrlCrawal urlCrawal;
    private UrlCrawal()
    {
        urlQueueManage=UrlQueueManage.getUrlQueueManageInstance();
        urlCollect=UrlCollect.getUrlCollectInstance();
        contentSave=ContentSave.getContentSaveInstance();
    }
    public static void UrlCrawal(String content,UrlQueueManage urlQueueManage)
    {

    }
    public void addWaitingHandleContent(HtmlContent content)
    {
        waitingforHandleQueue.add(content);
    }
    public synchronized static UrlCrawal getUrlCrawalInstance()
    {
        if(urlCrawal==null)
        {
            urlCrawal=new UrlCrawal();
        }
        return urlCrawal;
    }
    static Queue<HtmlContent> waitingforHandleQueue =new LinkedBlockingDeque<HtmlContent>();
}
