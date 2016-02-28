package NetTop;

import java.io.File;
import java.lang.ref.ReferenceQueue;

/**
 * Created by novas on 15/12/2.
 */
public class arequest implements Request
{
    public String requestUrl="http://127.0.0.1:18080/login-mobile";
  //  public String requestUrl="http://127.0.0.1:18080/uploads/g.jpg";
    public String user="novas";
    public String password="demo";
    public File[] files={new File("a.jpg"),new File("a.jpg")};
}
