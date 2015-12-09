package NCrawlMan.FastHtmlPraser;

public interface Factory<T> {
    public T getElementByName(String name);
}
