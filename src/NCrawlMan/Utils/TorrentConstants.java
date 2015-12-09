package NCrawlMan.Utils;

/**
 * Created by novas on 15/12/1.
 */
public class TorrentConstants {
    //支持的特性，可以进行图片的下载，存储在一个pic文件夹中；
    public static String DEFAULT_DOWNLOAD_DIR="/Users/novas/Desktop/NCrawlMan";
    //设置是否允许进行图片的下载,默认允许下载
    public static boolean IMAGEDOWNLOAD_ALLOWED=true;
    //保存文件的格式,图片的文件
    public static int FILE_TYPE_IMAGE=1;

    public static int FILE_TYPE_TEXT=2;

    //设置爬虫停止的条件，默认是以url
    public static int MAXURLCOUNT=100000;
}
