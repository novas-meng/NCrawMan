package NetTop;

import java.io.*;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by novas on 15/12/2.
 */
public class BaseNetTopBusiness {
    HttpURLConnection httpURLConnection;
    URL url;
    OutputStream outputStream;
    public NetTopListener listener;
    String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
    String PREFIX = "--" , LINE_END = "\r\n";
    String CONTENT_TYPE = "multipart/form-data"; //内容类型
    public void startRequest(Request request)
    {
        NetTopRequest netTopRequest=parseDataToNetTopRequest(request);
        startHttpUrlConnection(netTopRequest);
    }
    public byte[] convertMapToBytes(Map<String,String> dataParams)
    {
        StringBuilder sb=new StringBuilder();
        Set<Map.Entry<String,String>> set=dataParams.entrySet();
        Iterator<Map.Entry<String,String>> iterator=set.iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String,String> entry=iterator.next();
            sb.append(entry.getKey()+"="+entry.getValue()+"&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString().getBytes();
    }

    public void startHttpUrlConnection(NetTopRequest request)
    {
        try
        {
            url=new URL(request.requesturl);
            httpURLConnection=(HttpURLConnection)url.openConnection();
            HttpHeaderBuilder httpHeaderBuilder=HttpHeaderBuilder.getHttpHeaderBuilderInstance();
            httpHeaderBuilder.wrapHttpUrlConnection(httpURLConnection);
            httpURLConnection.connect();

            outputStream=httpURLConnection.getOutputStream();
            httpHeaderBuilder.build(new File("a.jpg"),request.dataParams,outputStream);
            outputStream.flush();
            outputStream.close();
            InputStream is=httpURLConnection.getInputStream();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void register(NetTopListener listener)
    {
        this.listener=listener;
    }
    public NetTopRequest parseDataToNetTopRequest(Request request)
    {
        System.out.println("in parse");
        Class cl=request.getClass();
        Field[] fields=cl.getFields();
        System.out.println("in parse"+fields.length+"   "+cl);
        Map<String,String> dataParams=new HashMap<>();
        String requestUrl=null;
        for(int i=0;i<fields.length;i++)
        {
            fields[i].setAccessible(true);

            try
            {
                if(fields[i].getName().equals("requestUrl"))
                {
                   requestUrl=fields[i].get(request).toString();
                }
                else
                {
                    dataParams.put(fields[i].getName(),fields[i].get(request).toString());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return new NetTopRequest(requestUrl,dataParams);
    }
}
