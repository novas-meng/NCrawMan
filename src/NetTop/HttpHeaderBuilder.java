package NetTop;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by novas on 15/12/3.
 */
public class HttpHeaderBuilder {
    private static HttpHeaderBuilder httpHeaderBuilder;
    String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
    String PREFIX = "--" , LINE_END = "\r\n";
    String CONTENT_TYPE = "multipart/form-data"; //内容类型
    private HttpHeaderBuilder()
    {

    }
    public  void wrapHttpUrlConnection(HttpURLConnection httpURLConnection)
    {
        httpURLConnection.setDoInput(true);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setChunkedStreamingMode(1024);
        httpURLConnection.setRequestProperty("connection", "keep-alive");
        httpURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
    }
    public static HttpHeaderBuilder getHttpHeaderBuilderInstance()
    {
        if(httpHeaderBuilder==null)
        {
            httpHeaderBuilder=new HttpHeaderBuilder();
        }
        return httpHeaderBuilder;
    }
    public void build(File file,OutputStream stream)throws Exception
    {
        build(file,null,stream);
    }
    public byte[] convertFileToByte(File file)throws Exception
    {
        InputStream is = new FileInputStream(file);
        /*
        byte[] bytes=new byte[1024];
        byte[] desbytes=new byte[1024];
        int length=0;
        int desloc=0;
        int lastlength=0;
        int k=0;
        while ((length=is.read(bytes))!=-1)
        {
            System.arraycopy(bytes,0,desbytes,desloc,length);
            byte[] temp=new byte[desbytes.length+1024];
            System.arraycopy(desbytes,0,temp,0,desbytes.length);
            desbytes=temp;
            k=desloc;
            desloc=desloc+length;
            lastlength=length;
        }
        bytes=new byte[k+lastlength];
        System.arraycopy(desbytes, 0, bytes, 0, bytes.length);
        is.close();
        desbytes=null;
        is=null;
        */
        byte[] bytes=HttpUtils.ConvertInputStreamToBytes(is);
        return bytes;
    }
    public void build(File file,Map<String,String> map,OutputStream outputStream)throws Exception
    {
        String LINE=PREFIX+BOUNDARY+LINE_END;
        StringBuffer sb = new StringBuffer();
        if(file!=null)
        {
            sb.append(LINE);
            sb.append("Content-Disposition: form-data; name=\"img\"; filename=\""+file.getName()+"\""+LINE_END);
            sb.append("Content-Type: application/octet-stream;"+LINE_END);
            sb.append(LINE_END);
            byte[] headerbytes=sb.toString().getBytes();
            byte[] bytes=convertFileToByte(file);

            byte[] end_data = (LINE_END).getBytes();
            byte[] all=new byte[headerbytes.length+bytes.length+end_data.length];
            System.arraycopy(headerbytes,0,all,0,headerbytes.length);
            System.arraycopy(bytes,0,all,headerbytes.length,bytes.length);
            System.arraycopy(end_data,0,all,headerbytes.length+bytes.length,end_data.length);
            outputStream.write(all);
        }
        for(Map.Entry<String,String> entry:map.entrySet())
        {
            sb=new StringBuffer();
            sb.append(LINE);
            sb.append("Content-Disposition: form-data; name=\""+entry.getKey() + LINE_END);
            sb.append(LINE_END);
            sb.append(entry.getValue());
            sb.append(LINE_END);
            outputStream.write(sb.toString().getBytes());
        }
        byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
        outputStream.write(end_data);
    }
    public void build(Map<String,String> map,OutputStream outputStream)throws Exception
    {
        build(null,map,outputStream);
    }
}
