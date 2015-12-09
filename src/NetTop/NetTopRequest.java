package NetTop;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by novas on 15/12/2.
 */
public class NetTopRequest implements Serializable
{
    private static final long serialVersionUID = -439476282014493612L;
    public String requesturl;
    public Map<String,String> dataParams;
    public NetTopRequest(String requesturl,Map<String,String> dataParams)
    {
        this.requesturl=requesturl;
        this.dataParams=dataParams;
    }
    public void destroy()
    {
        if(requesturl!=null)
        {
            requesturl=null;
        }
        if(dataParams!=null)
        {
            dataParams=null;
        }
    }
}
