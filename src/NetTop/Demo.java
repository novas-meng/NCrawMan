package NetTop;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Demo {
    public static void main(String[] args)throws Exception
    {
        InputStream is=new FileInputStream("temp.txt");
        byte[] bytes= HttpUtils.ConvertInputStreamToBytes(is);
        String jsonString=new String("{\"a\":\"b\"}");
       // System.out.println("json="+jsonString);
        long m=System.currentTimeMillis();
        Gson gson=new Gson();
      //  a product = new a();
        //将一个json字符串转换为java对象
     //   product = gson.fromJson(jsonString, a.class);
        long n=System.currentTimeMillis();
        System.out.println(n-m);
      //  System.out.println(product.a+"   ");
       // System.out.println(product.province);
   //  StringBuilder sb=new StringBuilder();
     //   sb.append("&}");
     //   sb.deleteCharAt(sb.length() - 1);
      //  System.out.println(sb.toString());
      //  arequest arequest=new arequest();
      //  BaseNetTopBusiness baseNetTopBusiness=new BaseNetTopBusiness();
       // baseNetTopBusiness.startRequest(arequest);
       // System.out.println(arequest.class.newInstance().a);
       // netTopBusiness.onSucess();
    }
}
