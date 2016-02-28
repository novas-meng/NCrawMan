package NetTop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by novas on 15/12/12.
 */

class edge
{
    public edge(int start,int end)
    {
        this.start=start;
        this.end=end;
    }
    int start;
    int end;

    @Override
    public String toString() {
        return start+"   "+end;
    }
}
/*
最小生成树算法，其中图的存储在mintree中，采用矩阵的形式存储图
 */
public class minGenerateTree
{
    //顶点的个数是7
    static int count=7;
    public static int[][] generateGraph(File file)throws Exception
    {
        int[][] graph=new int[count+1][count+1];
        BufferedReader br=new BufferedReader(new FileReader(file));
        String line=br.readLine();
        while (line!=null)
        {
            String[] var1= line.split(" ");
            int var2=Integer.valueOf(var1[0]);
            int var3=Integer.valueOf(var1[1]);
            int var4=Integer.valueOf(var1[2]);
            graph[var2][var3]=var4;
            graph[var3][var2]=var4;
            line=br.readLine();
        }
        return graph;
    }
    /*
    prim算法；基本思想；首先将图中的顶点划分为2部分；S和V-S，其中V是图中所有的顶点结合；
    S中的顶点是已经形成的最小生成树，那么对于剩余的顶点，每次挑出S中顶点和V-S中顶点的形成边的最小值，然后加入S中，这样肯定能保证S中不存在回路
    然后将这样的边加入到树中，当树的边数为定点数-1时，算法停止。
     */
    public static void prim(int[][] graph)
    {
        ArrayList<Integer> definelist=new ArrayList();
        ArrayList<Integer> notdifinelist=new ArrayList<>();
        definelist.add(1);
        for(int i=2;i<=count;i++)
        {
            notdifinelist.add(i);
        }
        ArrayList<edge> tree=new ArrayList<>();
        while (tree.size()<count-1)
        {
            int min=Integer.MAX_VALUE;
            int edgestart=0;
            int edgeend=0;
            for(int i=0;i<definelist.size();i++)
            {
                int start=definelist.get(i);
                //  System.out.println("size=" + definelist.size() + "   " + notdifinelist.size());
                for(int j=0;j<notdifinelist.size();j++)
                {
                    int end=notdifinelist.get(j);
                    int length=graph[start][end];
                    if(length!=0&&length<min)
                    {
                        min=length;
                        edgestart=start;
                        edgeend=end;
                    }
                }

            }
            tree.add(new edge(edgestart, edgeend));
            definelist.add(edgeend);
            //   System.out.println(edgestart+"   "+edgeend);
            int index=notdifinelist.indexOf(edgeend);
            notdifinelist.remove(index);
        }
        for(int i=0;i<tree.size();i++)
        {
            System.out.println(tree.get(i));
        }
    }
    public static void main(String[] args)throws Exception
    {
        //从1开始
        int[][] graph=generateGraph(new File("mintree.txt"));
        prim(graph);

        /*
        for(int i=0;i<graph.length;i++)
        {
            int[] subgrapg=graph[i];
            for(int j=0;j<subgrapg.length;j++)
            {
                System.out.print(subgrapg[j]);
            }
            System.out.println();
        }
        */
    }
}
