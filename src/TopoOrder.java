import NCrawlMan.Utils.FileDownloadMonitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

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
}
