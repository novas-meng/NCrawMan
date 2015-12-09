package NCrawlMan.Downloader;

/**
 * Created by novas on 15/11/29.
 */
/*

这个类的作用是存储url和对应url的内容，存储url的目的是，在从内容中提取url的时候，会用到url的信息
 */
public class HtmlContent {
    public String content;
    public  String url;

    public HtmlContent(String content,String url)
    {
        this.content=content;
        this.url=url;
    }
}
