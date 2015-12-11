package NCrawlMan.Downloader;

import NCrawlMan.Utils.TorrentConstants;

/**
 * Created by novas on 15/11/29.
 */
/*

这个类的作用是存储url和对应url的内容，存储url的目的是，在从内容中提取url的时候，会用到url的信息
 */
public class HtmlContent {

    public String content;
    public  String url;
    public  int type;
    public byte[] bytes;
    public HtmlContent(byte[] bytes,String url)
    {
        this.bytes=bytes;
        this.url=url;
    }
    //parse延迟加载的思想，将加载这些过程完全放在线程中
    public void parse()
    {
        if(this.url.endsWith(".jpg")||this.url.endsWith(".gif"))
        {
            this.type= TorrentConstants.FILE_TYPE_IMAGE;
        }
        else
        {
            this.type=TorrentConstants.FILE_TYPE_TEXT;
            String encode=getEncodingCode(new String(bytes,0,bytes.length>1024?1024:bytes.length));
            try
            {
                content=new String(bytes,encode);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                System.out.println("encode="+encode);
                content=new String(bytes);
            }
        }
    }

    public  String getEncodingCode(String header)
    {
        String index="charset=".intern();
        int loc=header.indexOf(index);
        int end=header.indexOf('"',loc);
        if(loc==-1)
        {
            return "utf-8";
        }
        return header.substring(loc+index.length(),end);
    }
}
