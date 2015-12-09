package NetTop;

/**
 * Created by novas on 15/12/2.
 */
public class NetTopBusiness extends BaseNetTopBusiness
{
    private static NetTopBusiness netTopBusiness;
    private NetTopBusiness()
    {

    }
    public static NetTopBusiness getNetTopBusinessInstance()
    {
        if(netTopBusiness==null)
        {
            netTopBusiness=new NetTopBusiness();
        }
        return netTopBusiness;
    }

    public void onSucess()
    {

    }
}
