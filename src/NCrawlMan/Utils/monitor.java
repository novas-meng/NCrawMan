package NCrawlMan.Utils;

/**
 * Created by novas on 15/12/1.
 */
public interface monitor {
    public void postSignal(monitor monitor,int signal);
    public void monitor();
    public void receiveSignal(int signal);
}
