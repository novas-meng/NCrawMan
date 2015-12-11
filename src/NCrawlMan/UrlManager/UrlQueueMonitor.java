package NCrawlMan.UrlManager;

import NCrawlMan.Utils.monitor;

import java.util.*;

/**
 * Created by novas on 15/11/30.
 */
//检测Url队列的情况，当一个队列中如果存储了大量的url的时候，那么需要将这些url先写入文件中，然后在进行取出
public class UrlQueueMonitor implements monitor
{

    private static UrlQueueMonitor urlQueueMonitor;
    private UrlQueueManage urlQueueManage;
    private UrlQueueMonitor()
    {

    }
    public static UrlQueueMonitor getUrlQueueMonitorInstance()
    {
        if(urlQueueMonitor==null)
        {
            urlQueueMonitor=new UrlQueueMonitor();
        }
        return urlQueueMonitor;
    }
    @Override
    public void postSignal(monitor monitor, int signal) {

    }

    @Override
    public void monitor() {
        urlQueueManage=UrlQueueManage.getUrlQueueManageInstance();
        HashMap<String,Queue<String>> map=urlQueueManage.getWaitingUrlMap();
        Set<Map.Entry<String,Queue<String>>> entrySet=map.entrySet();
       // Iterator<String,Queue<String>> iterator=entrySet.iterator();
    }

    @Override
    public void receiveSignal(int signal) {

    }
}
