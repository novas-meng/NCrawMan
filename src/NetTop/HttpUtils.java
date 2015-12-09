package NetTop;

import java.io.InputStream;

/**
 * Created by novas on 15/12/5.
 */
public class HttpUtils
{
    public static byte[] ConvertInputStreamToBytes(InputStream is)throws Exception
    {
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
        return bytes;
    }
}
