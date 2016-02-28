import NCrawlMan.Utils.FileDownloadMonitor;
import NetTop.HttpUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.UUID;

/**
 * Created by novas on 15/12/9.
 */
public class TopoOrder
{
    /*

    拓扑排序：
    1；首先确定每个节点的入度；
    2：将入度为0的节点加入到队列中
    3：从队列中取出一个元素A，输出；
    然后改变A指向的边的入度-1；
    然后进行判断，如果A指向的元素中有入度为0的，加入队列
    4：重复3.直到队列空
     */
    /*
    static HashMap<Integer,int[]> hashMap=new HashMap<>();
    public static int[] getIndegree()throws Exception
    {
        BufferedReader br=new BufferedReader(new FileReader("topoorder.txt"));
        String line=br.readLine();
        String[] args=line.split(" ");
        int[] in=new int[args.length];
        line=br.readLine();
        while(line!=null)
        {
            args=line.split(" ");
            int node=Integer.valueOf(args[0]);
            int[] nodes=new int[args.length-1];
            for(int i=1;i<args.length;i++)
            {
                int index=Integer.valueOf(args[i]);
                nodes[i-1]=index;
                in[index]++;
            }
            hashMap.put(node,nodes);
            line=br.readLine();
        }
        return in;
    }
    public static void main(String[] args) throws Exception
    {

        Queue<Integer> queue=new LinkedList();
        //获取每一个节点的入度
        int[] in=getIndegree();
        //找到入度为0的节点；
        for(int i=0;i<in.length;i++)
        {
            if(in[i]==0)
            {
                queue.add(i);
            }
        }
        System.out.println(queue.size());
        while (!queue.isEmpty())
        {
            int index=queue.poll();
            System.out.println("出列   "+index);
            int[] nodes=hashMap.get(index);
            for(int i=0;i<nodes.length;i++)
            {
                in[nodes[i]]--;
                if(in[nodes[i]]==0)
                {
                    System.out.println(nodes[i]);
                    queue.add(nodes[i]);
                }
            }
        }


    }
    */
    String host = "www.javathinker.org";
    int port = 80;
    Socket socket;
    URL url;
    public void createSocket() throws Exception {
       // url=new URL("http://tieba.baidu.com/p/4207375225");
        url=new URL("http://weibo.com/u/3045710232?from=feed&loc=nickname");
//        InetAddress inetAddress=InetAddress.getByAddress("123.125.104.197".getBytes());
        socket = new Socket(url.getHost(),80);
        System.out.println(url.getHost());
    }

    public void communicate() throws Exception {
        String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成
        String CONTENT_TYPE = "multipart/form-data"; //内容类型

        StringBuffer sb = new StringBuffer();
       // sb.append("Host: <A href="www.163.com" mce_href="www.163.com" target=_blank>www.163.com</A>/r/n");
       // sb.append("Connection: Keep-Alive/r/n");
      //  sb.append("Accept:text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
     //   sb.append("User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11) AppleWebKit/601.1.56 (KHTML, like Gecko) Version/9.0 Safari/601.1.56\r\n");
      //  sb.append("Content-Type:"+CONTENT_TYPE + ";boundary=" + BOUNDARY+"\r\n\r\n");
        /*
     */
        sb. append("GET " + "/"
                + " HTTP/1.1\n");
        sb.append("Accept:text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8\n");
        sb.append("Accept-Encoding:gzip, deflate, sdch\r\n")
                .append("Accept-Language:zh-CN,zh;q=0.8\r\n")
                .append("Cache-Control:max-age=0\r\n")
                .append("Connection:keep-alive\r\n")
                        .append("Content-Type:text/html; charset=UTF-8\r\n")
              //  .append("Cookie:BAIDUID=566A88F4C058A314AB8C0F1C5B88BAA6:FG=1; PSTM=1442195218; BIDUPSID=0DC1B9E53CA419F753588EE3EC342B01; BDUSS=E3Z0FGZHFWNUJjTDlMcnd4aW53OXBTWTFWajgyVU5ZV0dQTDE5eTk3M1hsbFZXQVFBQUFBJCQAAAAAAAAAAAEAAAAnFTETw87A77bgu6jC5AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAANcJLlbXCS5WWH; BDRCVFR[feWj1Vr5u3D]=I67x6TjHwwYf0; H_PS_645EC=53ccSgfOEHlA0khJeuJNW4cu8DSR5C4XEidF8utK7XLmInKjvdAjzUOba75DbC7AkeXs; BD_CK_SAM=1; BD_HOME=1; H_PS_PSSID=17745_1452_18281_18155_12825_10211_17000_17072_15524_12349_18005; BD_UPN=123253\r\n")
               // .append("Cookie:SINAGLOBAL=5852890296373.517.1437697366442; SUHB=0u5Re_6NXRZia5; SUB=_2AkMihlO7dcNhrABTmf8SxGLhaYlH-jjGieTAAH_tJkMxTUt-7axEOhvQhhU4_GCE56Rdt96P8L4R; SUBP=0033WrSXqPxfM72wWs9jqgMF55529P9D9WF2SpfZ8cOTqcz1YHO1cMeE5JpX5K2t; UOR=www.guofenchaxun.com,widget.weibo.com,www.baidu.com; login_sid_t=f36be5b10b2c6b2aa5faa08f204acf23; TC-Ugrow-G0=370f21725a3b0b57d0baaf8dd6f16a18; TC-Page-G0=4e714161a27175839f5a8e7411c8b98c; _s_tentry=-; Apache=1335273699369.2815.1449919576954; ULV=1449919576969:4:1:1:1335273699369.2815.1449919576954:1446727697252\r\n")
                .append("Host:" + url.getHost() + "\r\n")
                        .append("Referer:http://weibo.com/\n")
               // .append("Referer:https://www.baidu.com/link?url=Lyh4s37pG2xDXw02Y_6-CtHTvLvQEy6YR3XogJiFz9O&wd=&eqid=8bcd4880000e020800000002566c0c38\r\n")
              //  .append("If-Modified-Since:Sat, 12 Dec 2015 11:26:09 GMT\r\n")
              //  .append("Upgrade-Insecure-Requests:1\r\n")
                .append("User-Agent:Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.80 Safari/537.36" +
                        "Name\r\n\r\n");
        // 发出HTTP请求
        OutputStream socketOut = socket.getOutputStream();
        socketOut.write(sb.toString().getBytes());
        socket.shutdownOutput(); // 关闭输出流

        // 接收响应结果
        System.out.println(socket+"   "+ socket.getInetAddress()+socket.getInputStream());

        InputStream socketIn = socket.getInputStream();
       // System.out.println(socketIn.read());
        byte[] bytes= HttpUtils.ConvertInputStreamToBytes(socketIn);
        System.out.println(new String(bytes,"GB2312"));
        BufferedReader br = new BufferedReader(new InputStreamReader(socketIn));
        String data;
        while ((data = br.readLine()) != null) {
            System.out.println(data);
        }
        socket.close();
    }

    public static void main(String args[]) throws Exception {
        TopoOrder client = new TopoOrder();
        client.createSocket();
        client.communicate();
    }
}
