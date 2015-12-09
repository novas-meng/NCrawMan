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
        return (m>>bit&1)==1?false:true;
    }
    public static void setArray(int loc,long[] filter)
    {
        int index=loc/64;
        int bit=loc%64;
        filter[index]= (1<<bit)|filter[index];
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
    static int max(int[] codes)
    {
        int max=codes[0];
        if(codes[1]>max)
        {
            max=codes[1];
        }
        if(codes[2]>max)
        {
            max=codes[2];
        }
        if(codes[3]>max)
        {
            max=codes[3];
        }
        return max;
    }
    static int min(int[] codes)
    {
        int min=codes[0];
        if(codes[1]<min)
        {
            min=codes[1];
        }
        if(codes[2]<min)
        {
            min=codes[2];
        }
        if(codes[3]<min)
        {
            min=codes[3];
        }
        return min;
    }
    public static boolean checkExistAndSet(String url)
    {
        long longhashcode=mixHash(url);
        int shorthashcode=url.hashCode();
       // System.out.println(url);
       // System.out.println(longhashcode+"    "+shorthashcode);
        int[] codes=createHashArray(longhashcode,shorthashcode);
        /*
        if(min(codes)<min)
        {
            min=min(codes);
        }
        if(max(codes)>max)
        {
            max=max(codes);
        }
        */
      //  System.out.println(codes[0]+"   "+codes[1]+"   "+codes[2]+"   "+codes[3]);
        if(arrayiszero(codes[0],hostfilter)||arrayiszero(codes[1],hostfilter)||arrayiszero(codes[2],dirfilter)||arrayiszero(codes[3],dirfilter))
       // if(arrayiszero(codes[0]))
        {
            setArray(codes[0],hostfilter);
            setArray(codes[1],hostfilter);
            setArray(codes[2],dirfilter);
            setArray(codes[3],dirfilter);
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
    //生成hash的函数，将生成的64bit hash 拆分。
    public  static long MurmurHash64B ( String key)
    {
         int m = 0x5bd1e995;
         int r = 24;
         int len=key.length();
         int seed=0xEE6B27EB;
         int h1 = seed ^ len;
         int h2 = 0;

         char[] data = key.toCharArray();
        int count=0;
        while(len >= 8)
        {
             int k1 = data[count];
            count++;
            k1 *= m; k1 ^= k1 >> r; k1 *= m;
            h1 *= m; h1 ^= k1;
            len -= 4;

             int k2 = data[count];
            count++;
            k2 *= m; k2 ^= k2 >> r; k2 *= m;
            h2 *= m; h2 ^= k2;
            len -= 4;
        }
        switch(len)
        {
            case 3: h2 ^= data[2] << 16;
            case 2: h2 ^= data[1] << 8;
            case 1: h2 ^= data[0];
                h2 *= m;
        };
        if(len >= 4)
        {
             int k1 = data[count];
            count++;
            k1 *= m; k1 ^= k1 >> r; k1 *= m;
            h1 *= m; h1 ^= k1;
            len -= 4;
        }
        h1 ^= h2 >> 18; h1 *= m;
        h2 ^= h1 >> 22; h2 *= m;
        h1 ^= h2 >> 17; h1 *= m;
        h2 ^= h1 >> 19; h2 *= m;

         long h = h1;

        h = (h << 32) | h2;
        return h;
    }
   // byte[] fliter1=new byte[Integer.MAX_VALUE];
   // static long filter=0;

    public static void main(String[] args)throws Exception
    {
      //  double m=1/Math.log(2)*10000000*Math.log(1/0.001)/Math.log(2);
       // double k=Math.log(2)*m/10000000;
      //  System.out.println(m+" "+k);
      //  String ma="ddd";
       // ma.hashCode();
       // System.out.println(MurmurHash64B("www.baidu.com/test"));

        long m=175;
       // String url="http://img2.26tuugvlloppiyex/klluy/1zixbuxjqt4.jpg";
        BufferedReader bufferedReader=new BufferedReader(new FileReader("temp.txt"));
        String url=bufferedReader.readLine();
        long n=System.currentTimeMillis();
       // System.out.println(splitToHash(m,0,0));
        int count=0;
      //  String l="http://img2.228/1zixbuxjqt4.jpg";
        Set<String> set=new HashSet<>();
        int max=0;
        while (url!=null)
        {
          //  url=url+i;
          //  if(i%10000==0)
               // System.out.println(url);
/*
            if(url.startsWith("http://"))
            {
                try
                {
                   // int a=url.indexOf("http://");
                  //  int b=url.indexOf('/',a+1);
                    int c=url.indexOf('/',8);
                    if(c!=-1)
                    url=url.substring(c);
                }
                catch (Exception e)
                {
                    System.out.println(url);

                }
            }
            else
            {
                try
                {
                    int c=url.indexOf('/');
                    url=url.substring(c);
                }
                catch (Exception e)
                {
                    System.out.println("      "+url);

                }

            }
            */
            if(checkExistAndSet(url)) {
                //System.out.println("判断失误了");
                count++;
            }
            url=bufferedReader.readLine();
        }

        long mm=System.currentTimeMillis();
        System.out.println("time="+(mm-n)+"   "+count+"   "+set.size()+"   "+max);
    }

}
