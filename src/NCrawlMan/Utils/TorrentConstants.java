package NCrawlMan.Utils;

import java.util.ArrayList;

/**
 * Created by novas on 15/12/1.
 */
public class TorrentConstants {
    //支持的特性，可以进行图片的下载，存储在一个pic文件夹中；
    public static String DEFAULT_DOWNLOAD_DIR="/Users/novas/Desktop/NCrawlMan";
    //保存文件的格式,图片的文件
    public static int FILE_TYPE_IMAGE=1;

    public static int FILE_TYPE_TEXT=2;

    //设置爬虫停止的条件，默认是以url
    public static int MAXURLCOUNT=200;



    //设置每个线程等待抓取url的最大数量

    public static int MAX_QUEUE_URL_COUNT=500;

    //Url的过滤条件
    public static ArrayList<String> urlfilterlist=new ArrayList<>();

    //设置是否允许下载图片
    public static boolean ALLOWED_IMAGE_DOWNLOADED=true;

    public static boolean IMAGE_FIRST=true;
}
