package NetTop;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {
    private static class ReadTask implements Runnable {
        List<String> list;

        public ReadTask(List<String> list) {
            this.list = list;
        }

        public void run() {
            for (String str : list) {
                System.out.println(str);
            }
        }
    }

    /**
     * 写线程
     *
     * @author wangjie
     */
    private static class WriteTask implements Runnable {
        List<String> list;
        int index;

        public WriteTask(List<String> list, int index) {
            this.list = list;
            this.index = index;
        }

        public void run() {
            // list.remove(index);
            list.add("write_" + index);
            for (String str : list) {
                System.out.println("second......" + str);
            }
        }
    }

    public static void run() {
        final int NUM = 10;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < NUM; i++) {
            list.add("main_" + i);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(NUM);
        for (int i = 0; i < NUM; i++) {
            executorService.execute(new ReadTask(list));
            executorService.execute(new WriteTask(list, 4));
        }
        executorService.shutdown();

    }


    public static void main(String[] args) throws Exception {
      //  run();
        String m="003";
        Integer integer=Integer.parseInt(m);
        /*
        InputStream is=new FileInputStream("temp.txt");
        final byte[] bytes= HttpUtils.ConvertInputStreamToBytes(is);
        String jsonString=new String("{\"a\":\"b\"}");
       // System.out.println("json="+jsonString);
        long m=System.currentTimeMillis();
        Gson gson=new Gson();
      //  a product = new a();
        //将一个json字符串转换为java对象
     //   product = gson.fromJson(jsonString, a.class);
        long n=System.currentTimeMillis();
        System.out.println(n-m);
      //  System.out.println(product.a+"   ");
       // System.out.println(product.province);
   //  StringBuilder sb=new StringBuilder();
     //   sb.append("&}");
     //   sb.deleteCharAt(sb.length() - 1);
      //  System.out.println(sb.toString());
      */
/*
        arequest arequest=new arequest();
        BaseNetTopBusiness baseNetTopBusiness=new BaseNetTopBusiness(new NetTopListener() {
            @Override
            public void onSuccess(HttpResponse response) {
                System.out.println("成功");
                byte[] bytes=response.bytes;
                File file=new File("addd.jpg");
                try
                {
                    FileOutputStream fileOutputStream=new FileOutputStream(file);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail() {
               System.out.println("on fail");
            }

            @Override
            public void onError() {
                System.out.println("on error");

            }
        });
        baseNetTopBusiness.startRequest(arequest);
       // System.out.println(arequest.class.newInstance().a);
       // netTopBusiness.onSucess();
       */
    }
}
