package NCrawlMan.UrlManager;

/**
 * Created by novas on 15/11/27.
 */
public interface crawlqueue {
   // public String getCrawlUrl();
    public void addFailUrl(String Url);
    public void addSuccessUrl(String Url);
    public void addWaitingUrl(String Url);
}
