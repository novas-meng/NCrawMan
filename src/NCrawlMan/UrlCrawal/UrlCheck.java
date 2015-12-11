package NCrawlMan.UrlCrawal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by novas on 15/12/9.
 */
public class UrlCheck
{
    //可以进行一些url的过滤
    public static boolean checkUrl(String url)
    {
        boolean checked=true;
        ArrayList<String> arrayList=new ArrayList<>();
        for(int i=0;i<arrayList.size();i++)
        {
            if(url.contains(arrayList.get(i)))
            {
                checked=false;
            }
        }
        return checked;
    }
    //计算一个数的二进制表示
    public static int[] toBinary(int src)
    {
        int[] des=new int[17];
        int count=0;
        int var3=src;
        int var1=var3/2;
        int var2=var3%2;
        while (var1!=0)
        {
            try {
                des[count]=var2;
            }
            catch (Exception e)
            {
                System.out.println("error="+src);
                e.printStackTrace();
            }
            var3=var1;
            var1=var3/2;
            var2=var3%2;
            count++;
        }
        des[count]=var2;
        for(int i=0;i<des.length;i++)
        {
            if(des[i]==0)
            {
                des[i]=-1;
            }
        }
        return des;
    }
    public static int toInt(int[] src)
    {
        int var1=1;
        int var2=0;
        for(int i=0;i<src.length;i++)
        {
            if(src[i]>=1)
            {
                var2=var2+var1*src[i];
            }
            var1=var1*2;
        }
        return var2;
    }
    //计算url的值
    public static  int urlhash(String url)
    {
        /*
        int[] var1=new int[17];
        char[] chars=url.toCharArray();
        int hash=0;
        for(int i=0;i<chars.length;i++)
        {
           // hash=hash+chars[i];
            int[] var2=toBinary(chars[i]);
            for(int j=0;j<var2.length;j++)
            {
                var1[j]=var1[j]+var2[j];
            }
        }
        /*
        for(int i=0;i<var1.length;i++)
        {
            if(var1[i]>0)
            {
                var1[i]=1;
            }
            else
            {
                var1[i]=0;
            }
        }

        return toInt(var1);
        */
        int hash=0;
        char[] chars=url.toCharArray();
        for(int i=0;i<chars.length;i++)
        {
            hash+=chars[i]*(chars.length-i);
        }
        return hash;
    }
    public static ArrayList<String> urlSort(Set<String> urlset,String baseurl)
    {
        //List<String> list=Arrays.asList(urlset);
        ArrayList<String> list=new ArrayList<>();
        int basedistance=urlhash(baseurl);
        String[] strings=new String[urlset.size()];
        //用一个数组记录url与baseurl的距离
        int[] distance=new int[strings.length];
        //用一个数组记录顺序
        int[] index=new int[strings.length];
        urlset.toArray(strings);
       // System.out.println(strings.length);
        for(int i=0;i<strings.length;i++)
        {
            distance[i]=Math.abs(urlhash(strings[i])-basedistance);
          //  System.out.println("...."+strings[i]+"   "+distance[i]);
        }
        int[] var1=new int[distance.length];
        System.arraycopy(distance,0,var1,0,var1.length);
        for(int i=0;i<distance.length;i++)
        {
            int key=distance[i];
            int loc=i;
            for (int j=loc+1;j<distance.length;j++)
            {
                if(distance[j]<key)
                {
                    key=distance[j];
                    loc=j;
                }
            }
            int temp=distance[i];
            distance[i]=key;
            distance[loc]=temp;
                    /*
            for(int j=distance.length-1;j>i;j--)
            {
                if(distance[j]<distance[j-1])
                {
                    int temp=distance[j-1];
                    distance[j-1]=distance[j];
                    distance[j]=temp;
                }

            }
            */
        }
        for (int i=0;i<distance.length;i++)
        {
            for(int j=0;j<var1.length;j++)
            {
                if(var1[j]==distance[i])
                {
                    index[i]=j;
                    var1[j]=-1;
                    break;
                }
            }
        }
        urlset.clear();
        urlset=null;
        for(int i=0;i<strings.length;i++)
        {
            list.add(strings[index[i]]);
        }
      //  System.out.println(list.size());
        for(int i=0;i<list.size();i++)
        {
            // System.out.println("...."+list.get(i));

        }

        return list;
    }
    //处理url，比如类似于www.baidu.com这样没有协议头的
    public static String wrapUrl(String url)
    {
        if(!url.startsWith("http"))
        {
            url="http://"+url;
        }
        return url;
    }
}
