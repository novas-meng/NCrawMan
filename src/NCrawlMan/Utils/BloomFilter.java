package NCrawlMan.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by novas on 15/11/27.
 */
//布隆过滤器，采用三个hash值确定，数组容量为1050000个long；其中一个long为64位，那么最大的位数为64000000
public class BloomFilter {
    static int max=0;
    static int min=Integer.MAX_VALUE;
    //用来进行判断的数组,filter要具体到bit，一个long 64bit，所以最大67200000,可用26bit表示，所以选用int来存储hash；
    static long[] hostfilter=new long[1050000];
    static long[] dirfilter=new long[1050000];

    //1000000
   //查询对应bit是否已经设置了
    public static boolean arrayiszero(int loc,long[] filter)
    {
        int index=loc/64;
        int bit=loc%64;
        long m=filter[index];
      //  System.out.println("..."+(m>>bit&1));
        return (m>>bit&1)==1?false:true;
    }
    //filter设置bit位
    public static void setArray(int loc,long[] filter)
    {
        int index=loc/64;
        int bit=loc%64;
        long m=1;
      //  System.out.println(index+"  "+bit+"  "+filter[index]);
        filter[index]= (m<<bit)|filter[index];
      //  System.out.println(filter[index]+" "+Math.pow(2,47)+"  "+(1<<12));
    }
    public static int JSHash(String str)
    {
        int hash = 1315423911;

        for(int i = 0; i < str.length(); i++)
        {
            hash ^= ((hash << 5) + str.charAt(i) + (hash >> 2));
        }

        return (hash & 0x7FFFFFFF);
    }
    public static int FNVHash1(String data)
    {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for(int i=0;i<data.length();i++)
            hash = (hash ^ data.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        return hash;
    }
    public static long mixHash(String str)
    {
        long hash = JSHash(str);
        hash <<= 32;
        hash |= FNVHash1(str);
        return hash;
    }

    public static synchronized boolean checkExistAndSet(String url)
    {
        long longhashcode=mixHash(url);
        int shorthashcode=url.hashCode();
        int[] codes=createHashArray(longhashcode,shorthashcode);
        if(arrayiszero(codes[0],hostfilter)||arrayiszero(codes[1],hostfilter)||arrayiszero(codes[2],dirfilter)||arrayiszero(codes[3],dirfilter))
        {
            setArray(codes[0],hostfilter);
           // System.out.println("   "+arrayiszero(codes[0], hostfilter));
            setArray(codes[1], hostfilter);
           // System.out.println("    "+arrayiszero(codes[1], hostfilter));

            setArray(codes[2], dirfilter);
          //  System.out.println("    "+arrayiszero(codes[2], dirfilter) + " " + codes[2]);

            setArray(codes[3], dirfilter);
         //   System.out.println("    "+arrayiszero(codes[3], dirfilter));

            return false;
        }
        return true;
    }
    public static int[] createHashArray(long longhashcode,int shorthashcode)
    {
        int[] codes=new int[4];
        try {

            codes[0]=splitToHash(longhashcode,0,26);
            codes[1]=splitToHash(longhashcode,26,26)%65535*8;
            codes[2]=splitToHash(longhashcode,38,26);
            codes[3]=splitToHash(shorthashcode,0,26)%65535*8;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return codes;
    }

    //将一个long 截取length bit ，成为一个int
    public static int splitToHash(long m,int start,int length) throws Exception
    {
       //String binaryString= Long.toBinaryString(m);
        if(length>26)
        {
            throw new Exception("length must be lower than 26");
        }
        long res=0;
        long n=m >> start;
        int count=0;
        long var1=n;
        int zerocount=0;
        while(count<length)
        {
            long var2=var1&1;
            if(var2==1)
            {
                if(zerocount!=0)
                {
                    zerocount=0;
                    long var3=1<<count;
                    res=res|var3;
                }
                else
                {
                    res=res<<1;
                    res=res|var2;
                }
            }
            else
            {
                zerocount++;
            }
            var1=var1>>1;
            count++;
        }
      //  res=res>>1;
        return (int)res;
    }
}
