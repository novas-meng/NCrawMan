package NetTop;

import java.util.ArrayList;

/**
 * Created by novas on 15/12/6.
 */
public class bean {
    class abean
    {
        String name;
        ArrayList<cbean> province=new ArrayList<>();
    }
    class cbean
    {
        String name;
        ArrayList<dbean> cities=new ArrayList<>();
    }
class dbean
{
    ArrayList<String> city=new ArrayList<>();
}

    String name;
    ArrayList<abean> province=new ArrayList<>();

}
