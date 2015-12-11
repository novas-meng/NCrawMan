package NCrawlMan.initConf;

import NCrawlMan.Utils.TorrentConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by novas on 15/11/26.
 */
public class torrent
{
    public  int threadcount=4;
    //添加一些url过滤条件
    public torrent()
    {
        TorrentConstants.urlfilterlist.add(".xml");
        /*
        js文件中可能包含着指向的url，不能删除
         */
      //  TorrentConstants.urlfilterlist.add(".js");
        TorrentConstants.urlfilterlist.add(".css");
    }
    public void setImageDownloadAllowed(boolean allowed)
    {
        TorrentConstants.ALLOWED_IMAGE_DOWNLOADED=allowed;
    }
    public void setThreadcount(int threadcount)
    {
        this.threadcount=threadcount;
    }
    public void setUrlFilter(String filter)
    {
        TorrentConstants.urlfilterlist.add(filter);
    }
    public void setUrlFilter(ArrayList<String> list)
    {
        TorrentConstants.urlfilterlist.addAll(list);
    }
    private ArrayList<String> rootUrlList=new ArrayList<>();
    public void addRootUrlList(String url)
    {
        rootUrlList.add(url);
    }
    public void addRootUrlList(List<String> list)
    {
        this.rootUrlList.addAll(list);
    }
    public ArrayList<String> getRootUrlList( )
    {
        return rootUrlList;
    }
}
