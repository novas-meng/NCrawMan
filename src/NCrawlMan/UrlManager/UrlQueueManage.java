package NCrawlMan.UrlManager;

import NCrawlMan.Utils.SignalConstants;
import NCrawlMan.Utils.TorrentConstants;

import java.io.File;
import java.util.*;

/**
 * Created by novas on 15/11/27.
 */
public class UrlQueueManage implements crawlqueue
{

    //线程和线程对应的url队列,这个队列中存储的是等待爬取的链接
    private  HashMap<String,LinkedList<String>> waitingUrlMap=new HashMap<>();
    //爬取失败的链接
    private  Queue<String> failQueue=new LinkedList<>();
    //用于监测waitingUrlMap这个map中url个数，防止map中url个数太多
    private UrlQueueMonitor urlQueueMonitor;
    //使用文件保存成功的链接
    File successFile=new File("success_url.ini");
    //使用文件保存失败的链接
    File failFile=new File("fail_url.ini");
    private UrlQueueManage()
    {
        urlQueueMonitor=UrlQueueMonitor.getUrlQueueMonitorInstance();
    }
    private static UrlQueueManage urlQueueManage;
    public synchronized static UrlQueueManage getUrlQueueManageInstance()
    {
        if(urlQueueManage==null)
        {
            urlQueueManage=new UrlQueueManage();
        }
        return urlQueueManage;
    }
    @Override
    public void addFailUrl(String Url) {

    }

    @Override
    public void addSuccessUrl(String Url) {

    }
    public java.util.HashMap<String, LinkedList<String>> getWaitingUrlMap() {
        return waitingUrlMap;
    }

    //这里涉及url调度的问题，将新来的url添加到url数量最少的那个队列
    @Override
    public void addWaitingUrl(String url) {
       //  waitingQueue.add(Url);
        Set<Map.Entry<String,LinkedList<String>>> set=waitingUrlMap.entrySet();
        Iterator<Map.Entry<String,LinkedList<String>>> iterator=set.iterator();
        String key=null;
        int min=Integer.MAX_VALUE;
        while(iterator.hasNext())
        {
            Map.Entry<String,LinkedList<String>> entry=iterator.next();
           // System.out.println(entry.getKey()+"   "+entry.getValue().size());
            if(entry.getValue().size()<min)
            {
                min=entry.getValue().size();
                key=entry.getKey();
               // System.out.println("min="+min+"key="+key);
            }
        }
      //  System.out.println("添加的队列为"+key);
        Queue<String> queue=waitingUrlMap.get(key);
        queue.add(url);
        //进行判断当队列中url数量太多的时候，将url写入文件中
        if(queue.size()> TorrentConstants.MAX_QUEUE_URL_COUNT)
        {
            urlQueueMonitor.postSignal(urlQueueMonitor, SignalConstants.ABOVE_URL_MAXCOUNT);
        }
        //开启url数量检测
       // urlQueueMonitor.monitor();
    }

    //如果相关性特别强的话，直接加入头部，优先级最高
    public void addFirstWaitingUrl(String url) {
        //  waitingQueue.add(Url);
        Set<Map.Entry<String,LinkedList<String>>> set=waitingUrlMap.entrySet();
        Iterator<Map.Entry<String,LinkedList<String>>> iterator=set.iterator();
        String key=null;
        int min=Integer.MAX_VALUE;
        while(iterator.hasNext())
        {
            Map.Entry<String,LinkedList<String>> entry=iterator.next();
            // System.out.println(entry.getKey()+"   "+entry.getValue().size());
            if(entry.getValue().size()<min)
            {
                min=entry.getValue().size();
                key=entry.getKey();
                // System.out.println("min="+min+"key="+key);
            }
        }
        //  System.out.println("添加的队列为"+key);
        LinkedList<String> queue=waitingUrlMap.get(key);
        queue.addFirst(url);
        //进行判断当队列中url数量太多的时候，将url写入文件中
        if(queue.size()> TorrentConstants.MAX_QUEUE_URL_COUNT)
        {
            urlQueueMonitor.postSignal(urlQueueMonitor, SignalConstants.ABOVE_URL_MAXCOUNT);
        }
        //开启url数量检测
        // urlQueueMonitor.monitor();
    }

    public String provideUrlForHttpDownload(String currentThreadName)
    {
       // System.out.println("当前获取连接的线程为"+currentThreadName);
        Queue<String> waitingQueue=waitingUrlMap.get(currentThreadName);
        while (waitingQueue.isEmpty())
        {

        }
      //  System.out.println("等待爬去url个数" + waitingQueue.size());
        return waitingQueue.poll();
    }
    public boolean hasWaitUrl(String currentThreadName)
    {
        Queue<String> waitingQueue=waitingUrlMap.get(currentThreadName);
           return waitingQueue.isEmpty()?false:true;
    }

    public void addWaitingUrl(List<String> list)
    {
        for(int i=0;i<list.size();i++)
        {
            addWaitingUrl(list.get(i));
        }
    }
    //初始化线程对应的抓取队列
    public void initWaitUrlMap(String[] names)
    {

        for(int i=0;i<names.length;i++)
        {
            LinkedList<String> queue=new LinkedList<>();
            waitingUrlMap.put(names[i],queue);
        }
    }
}
