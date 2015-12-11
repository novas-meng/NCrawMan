package NCrawlMan.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 * Created by novas on 15/12/1.
 */
//这个类负责将内容写入到文件中，同时，写入文件的时候，改变FileDownloadminitor的状态
public class ContentSave
{
    private static ContentSave contentSave;
    private FileDownloadMonitor fileDownloadMonitor;
    private ContentSave()
    {
       File dir=new File(TorrentConstants.DEFAULT_DOWNLOAD_DIR);
        if(!dir.exists())
        {
            dir.mkdirs();
        }
    }
    public static ContentSave getContentSaveInstance()
    {
        if(contentSave==null)
        {
            contentSave=new ContentSave();
        }
        return contentSave;
    }
    public String transferUrlToFilename(String url)
    {
        char[] chars=url.toCharArray();
        for(int i=0;i<chars.length;i++)
        {
            if(chars[i]== File.separatorChar||chars[i]==File.pathSeparatorChar)
            {
                chars[i]='_';
            }
        }
        String filename=new String(chars);
        return filename;
    }
    public void saveImageToFile(String url,byte[] content)
    {
        File f=null;
        FileOutputStream fw=null;
        f=new File(TorrentConstants.DEFAULT_DOWNLOAD_DIR,transferUrlToFilename(url));
        try
        {
            fw=new FileOutputStream(f);
            fw.write(content);
            fw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        fileDownloadMonitor=FileDownloadMonitor.getFileDownloadMonitorInstance();
        fileDownloadMonitor.addDownloadUrlCount();
    }
    //每次成功写入一个网页文本内容，FileDownloadMonitor便会增加
    public void saveTextToFile(String url,String content)
    {
         File f=null;
         FileWriter fw=null;
         f=new File(TorrentConstants.DEFAULT_DOWNLOAD_DIR,transferUrlToFilename(url));
        try
        {
            fw=new FileWriter(f);
            fw.write(content);
            fw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        BloomFilter.checkExistAndSet(url);
        fileDownloadMonitor=FileDownloadMonitor.getFileDownloadMonitorInstance();
        fileDownloadMonitor.addDownloadUrlCount();
    }

}
