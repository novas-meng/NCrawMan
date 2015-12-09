package NCrawlMan.initConf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by novas on 15/11/26.
 */
public class torrent
{
    public  int threadcount=4;
    public void setThreadcount(int threadcount)
    {
        this.threadcount=threadcount;
    }
    int maxLinkCount=10000;
    int maxFileSize=100*1024*1024;
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
