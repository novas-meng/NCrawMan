package NCrawlMan.Utils;

import NCrawlMan.Downloader.ThreadPool;

import java.io.File;
import java.io.FileWriter;

/**
 * Created by novas on 15/12/1.
 */
//检测下载文件夹的大小，如果达到停止条件，那么就发出停止信息；
public class FileDownloadMonitor implements monitor
{
    //单例模式生成文件监测者
    private static FileDownloadMonitor fileDownloadMonitor;
    private  int urldownloadcount=0;
    private ThreadPool threadMonitor;
    //每成功
    public  void addDownloadUrlCount()
    {
        urldownloadcount++;
        System.out.println("已经接受到的url个数为="+urldownloadcount);
        if(urldownloadcount>=TorrentConstants.MAXURLCOUNT)
        {
            threadMonitor=ThreadPool.getThreadPoolInstance();
            postSignal(threadMonitor,SignalConstants.APP_STOPED);
        }
    }

    private FileDownloadMonitor()
    {
        File textdir=new File(TorrentConstants.DEFAULT_DOWNLOAD_DIR,"TEXT");
        textdir.mkdirs();
        File picdir=new File(TorrentConstants.DEFAULT_DOWNLOAD_DIR,"PIC");
        picdir.mkdirs();
    }
    public static FileDownloadMonitor getFileDownloadMonitorInstance()
    {
        if(fileDownloadMonitor==null)
        {
            fileDownloadMonitor=new FileDownloadMonitor();
        }
        return fileDownloadMonitor;
    }
    @Override
    public void monitor() {

    }

    @Override
    public void postSignal(monitor monitor, int signal) {
         monitor.receiveSignal(signal);
    }

    @Override
    public void receiveSignal(int signal) {

    }
}
