package NCrawlMan.Downloader;

import NCrawlMan.UrlManager.UrlQueueManage;
import NCrawlMan.initConf.torrent;

/**
 * Created by novas on 15/11/27.
 */
public class Spider {
    torrent torrent;
    UrlQueueManage manage;
    ThreadPool threadPool;
    public Spider(torrent torrent)
    {
        this.torrent=torrent;
        init();
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
     //   httpDownload.download();
       // init();
        threadPool.run();
    }
}
