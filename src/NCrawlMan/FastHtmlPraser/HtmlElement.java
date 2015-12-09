package NCrawlMan.FastHtmlPraser;

public class HtmlElement {
    String name;
    int[] locs=new int[20];
    int count=0;
    //HtmlElement这样的元素可以重复使用，但是每次一定要将locs和count重置
    public void reset()
    {
    	locs=new int[20];
    	count=0;
    }
    public int getlabelCount()
    {
    	return count;
    }
    public HtmlElement(String name)
    {
    	this.name=name;
    }
    public String getElementName()
    {
    	return this.name;
    }
    public void addLoc(int loc)
    {
    	if(count==locs.length)
    	{
    		int[] temp=new int[locs.length+20];
    		System.arraycopy(locs,0,temp,0,locs.length);
    		locs=temp;
    	}
    	locs[count]=loc;
    	count++;
    //	System.out.println("count增加");
    }
}
