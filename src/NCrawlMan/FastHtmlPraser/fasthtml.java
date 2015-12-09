package NCrawlMan.FastHtmlPraser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class fasthtml {
   private static fasthtml fasthtml;
   char[] array;
   private ElementFactory factory;
   private String last;
   private fasthtml()
   {
	   factory=ElementFactory.getElementFactoryInstance();
   }
   public static fasthtml getFastHtmlInstance()
   {
	   if(fasthtml==null)
	   {
		   fasthtml=new fasthtml();
	   }
	   return fasthtml;
   }
   public ArrayList<HtmlLabel> select(String elementname)
   {
	   /*
	   Map<String,HtmlElement> map=this.factory.getElementMap();
	   Set<Entry<String, HtmlElement>> set=map.entrySet();
	   Iterator<Entry<String, HtmlElement>> it=set.iterator();
	   while(it.hasNext())
	   {
		   System.out.println(it.next().getKey());
	   }
	   */
	   ArrayList<HtmlLabel> list=new ArrayList<HtmlLabel>();
	   HtmlElement element=this.factory.getElementByName(elementname);
	   int[] locs=element.locs;
	   if(element.count%2==1)
		   element.count--;
	//   System.out.println("length="+array.length+"   "+locs.length+"  "+element.count+"   "+element.getlabelCount());
	   for(int i=0;i<element.count;i++)
	   {
		  // System.out.println(array[i]);
		   int start=locs[i];
		   i++;
		   int end=locs[i];
		 //  System.out.println(start+"  "+end);
		   char[] ch=new char[end-start];
		   System.arraycopy(this.array, start, ch, 0, ch.length);
		//   System.out.println(new String(ch)+"   "+start+"   "+end);
		   list.add(new HtmlLabel(elementname,new String(ch)));
		 //  System.out.println("======");
	   }
	   return list;
   }
  public void parse(String htmlContent)
  {
	  array=htmlContent.toCharArray();
	  for(int i=0;i<array.length;i++)
	  {
		  if(array[i]=='/'&&array[i+1]=='>')
		  {
			  HtmlElement element=factory.getElementByName(last);
			// System.out.println("end    "+element.getlabelCount()+"   "+last+"   "+last.length()+"  "+element);
			  if(element.getlabelCount()%2==1)
			  element.addLoc(i+1);
		  }
		  //处理</name>和<name>这样的情况
		  if(array[i]=='<'&&array[i+1]!='!')
		  {
			  i++;
			  if(array[i]=='/')
			  {
				  int j=i+1;
				  i++;
				  while(array[i]!=' '&&array[i]!='>')
				  {
					  i++;
				  }
				  char[] ch=new char[i-j];
				  System.arraycopy(array, j, ch, 0, ch.length);
				  String label=new String(ch);
				  HtmlElement element=factory.getElementByName(label);
				  last=label;
			//	  System.out.println("end    "+element.getlabelCount()+"   "+last+"   "+last.length()+element);
				  if(element.getlabelCount()%2==1)
				  element.addLoc(j-2);
			  }
			  else
			  {
				  int j=i;
				  while(array[i]!=' '&&array[i]!='>')
				  {
					  i++;
				  }
				  char[] ch=new char[i-j];
				  System.arraycopy(array, j, ch, 0, ch.length);
				  String label=new String(ch);
				  HtmlElement element=factory.getElementByName(label);
				  last=label;
				  if(element.getlabelCount()%2==0)
				  {
					//  System.out.println("start    "+element.getlabelCount()+"   "+last+"   "+last.length()+"  "+element);
					  element.addLoc(i+1);
				  }
			  } 
		  }
	  }
  }
}
