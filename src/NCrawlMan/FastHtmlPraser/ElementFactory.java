package NCrawlMan.FastHtmlPraser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ElementFactory implements Factory<HtmlElement>
{
	//单例模式生成工厂对象，享元模式生成HtmlElement对象
    private static ElementFactory elementFactory;
    private   Map<String,HtmlElement> elementMap=new HashMap<String,HtmlElement>();
    private ElementFactory()
    {
    	
    }
    /*
    //其中elementMap中存储的label是可以重用的，但是需要将label的一些信息进行重置
    public void dataReset()
    {
    	  Set<Map.Entry<String,HtmlElement>> set=elementMap.entrySet();
    	  Iterator<Map.Entry<String, HtmlElement>> it=set.iterator();
    	  while(it.hasNext())
    	  {
    		 Map.Entry<String, HtmlElement> entry=it.next();
    		 entry.getValue().reset();
    	  }
    }
    */
    public static ElementFactory getElementFactoryInstance()
    {
    	if(elementFactory==null)
    	{
    		elementFactory=new ElementFactory();
    	}
    	return elementFactory;
    }
	@Override
	public HtmlElement getElementByName(String name) {
		// TODO Auto-generated method stub
		name=name.toLowerCase();
		if(elementMap.get(name)==null)
		{
			HtmlElement element=new HtmlElement(name);
			elementMap.put(name, element);
		}
		return elementMap.get(name);
	}
	public  Map<String,HtmlElement> getElementMap()
	{
		return this.elementMap;
	}
}
