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
    public BaseNetTopBusiness(NetTopListener listener)
    {
        this.listener=listener;
    }
    //开启Http请求，会根据结果调用listener的方法.
    public HttpResponse startRequest(Request request)
    {
        NetTopRequest netTopRequest=HttpUtils.parseDataToNetTopRequest(request);
        HttpResponse response=startHttpUrlConnection(netTopRequest);
        return response;
    }
    public HttpResponse startHttpUrlConnection(NetTopRequest request)
    {
        HttpResponse response;
        if(request.responseType==HttpResponseType.RESPONSE_TYPE_TEXT)
        {
           response= startHttpTextConnection(request);
        }
        else
        {
            response=startHttpImageConnection(request);
        }
        return response;
    }
    //从服务器获取图片,get情况
    public HttpResponse startHttpImageConnection(NetTopRequest request)
    {
        HttpResponse response=null;
        try {
            url = new URL(request.requesturl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200)
            {
                InputStream is = httpURLConnection.getInputStream();
                byte[] bytes = HttpUtils.ConvertInputStreamToBytes(is);
                response=new HttpResponse(bytes);
                listener.onSuccess(response);
            }
            else
            {
                listener.onFail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onError();
        }
        return response;
    }
    //post情况
    public HttpResponse startHttpTextConnection(NetTopRequest request) {
        HttpResponse response=null;
        try {
            url = new URL(request.requesturl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            HttpHeaderBuilder httpHeaderBuilder = HttpHeaderBuilder.getHttpHeaderBuilderInstance();
            httpHeaderBuilder.wrapHttpUrlConnection(httpURLConnection);
            httpURLConnection.connect();

            outputStream = httpURLConnection.getOutputStream();
           // File[] files = new File[0];
           // files[0] = new File("a.jpg");
          //  files[1] = new File("a.jpg");
            httpHeaderBuilder.build(request.files, request.dataParams, outputStream);
            outputStream.flush();
            outputStream.close();
            if(httpURLConnection.getResponseCode()==200)
            {
                InputStream is = httpURLConnection.getInputStream();
                byte[] bytes = HttpUtils.ConvertInputStreamToBytes(is);
                response=new HttpResponse(bytes);
                listener.onSuccess(response);
            }
            else
            {
                listener.onFail();
            }

        } catch (Exception e) {
            e.printStackTrace();
            listener.onError();
        }
        return response;
    }
}
