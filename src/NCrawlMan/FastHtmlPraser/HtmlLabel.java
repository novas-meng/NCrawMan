package NCrawlMan.FastHtmlPraser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HtmlLabel {
     String name;
     HashMap<String,String> attrsMap=new HashMap<String,String>();
     String labelValue;
     public HtmlLabel(String name,String content)
     {
    	 this.name=name;
    	 parse(content);
     }
     public String wrap(String content)
     {
    	 int begin=-1;
    	 int end=content.length();
    	 for(int i=0;i<content.length();i++)
    	 {
    		 char ch=content.charAt(i);
    		 if(ch!=10&&ch!=32)
    		 {
    			 begin=i;
    			 break;
    		 }
    	 }
    	// System.out.println((int)content.charAt(begin));
    	 for(int i=content.length()-1;i>=0;i--)
    	 {
    		 char ch=content.charAt(i);
    		 if(ch!=10&&ch!=32)
    		 {
    			 end=i;
    			 break;
    		 }
    	 }
    	// System.out.println(content);
    //	 System.out.println(content.length()+"  "+begin+"   "+end+"  ");
    	 if(begin==-1)
    	 {
    		 return null;
    	 }
    	 return content.substring(begin, end+1);
     }
     public void parse(String content)
     {
    	 
    	 int lastindex=content.lastIndexOf(">");
    	 if(lastindex==-1)
    	 {
    		 this.labelValue=content;
    	 }
    	 else
    	 {
    		 this.labelValue=content.substring(lastindex+1);
    	 }
    	 if(this.labelValue.length()>0)
    	 {
    		this.labelValue= wrap(this.labelValue);
    	 }
    		 System.out.println(content);
    	// System.out.println("===========");
    //	 if(this.labelValue!=null&&this.labelValue.length()>4)
    //	 System.out.println(this.labelValue);
    	 char[] array=content.toCharArray();
    	 int a=content.indexOf('>');
    	 int b=content.indexOf("<");
    	 if(a==-1)
    	 {
    		 a=Integer.MAX_VALUE;
    	 }
    	 if(b==-1)
    	 {
    		 b=Integer.MAX_VALUE;
    	 }
    	int c=a<b?a:b;
    	if(c==Integer.MAX_VALUE)
    	{
    		c=content.length()-1;
    	}
    	int begin=0;
    	String key=null;
		String value="";
		//System.out.println("====);
    	for(int i=0;i<=c&&c<Integer.MAX_VALUE;i++)
    	{
    		if(value!=null&&array[i]=='=')
    		{
    			key=content.substring(begin,i);
    		//	System.out.println("key="+key);
    			begin=i+1;
    			value=null;
    		}
    		if(key!=null&&(array[i]==' '||array[i]=='>')&&(array[i-1]=='\''||array[i-1]=='"'))
    		{
    			value=content.substring(begin+1, i-1);
    		//	System.out.println("value="+value);
    			begin=i+1;
    			attrsMap.put(key, value);
    			key=null;
    		}
    	}
    	  HashMap<String,String> map=this.attrsMap;
    	  Set<Map.Entry<String,String>> set=map.entrySet();
    	  Iterator<Map.Entry<String, String>> it=set.iterator();
    	  while(it.hasNext())
    	  {
    		 Map.Entry<String, String> entry=it.next();
    		// System.out.println(entry.getKey()+"   "+entry.getValue());
    	  }
    	//  System.out.println("===");
     }
     public String getAttrValue(String attrname)
     {
    	 return attrsMap.get(attrname);
     }
}
