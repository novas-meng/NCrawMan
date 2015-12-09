package NCrawlMan.UrlCrawal;

import NCrawlMan.Downloader.HtmlContent;
import NCrawlMan.UrlManager.UrlQueueManage;
import NCrawlMan.Utils.ContentSave;

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
    //后台进程，负责从获取到的内容中提取出url,然后将内容保存到文件
    private static class QueueHandler extends Thread
    {
        public QueueHandler(ThreadGroup group,String name)
        {
            super(group, name);
        }
        @Override
        public void run() {
            System.out.println("后台进程启动");
            for(;;)
            {
                synchronized (lock)
                {
                    if(!waitingforHandleQueue.isEmpty())
                    {
                        System.out.println("=======");
                        HtmlContent htmlcontent=waitingforHandleQueue.poll();
                        urlCollect.collect(htmlcontent.content,htmlcontent.url);
                        contentSave.saveTextToFile(htmlcontent.url,htmlcontent.content);
                    }
                }
            }
        }
    }
    static
    {
        ThreadGroup group=Thread.currentThread().getThreadGroup();
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        for (ThreadGroup tgn = tg;
             tgn != null;
             tg = tgn, tgn = tg.getParent());
        Thread handler=new QueueHandler(group,"QueueHandler");
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
