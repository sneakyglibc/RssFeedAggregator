package rss_feed.rssreader.Data;

/**
 * Created by Phil on 01/05/2016.
 */
public class ItemNode {
    private String      link;
    private String      title;

    public ItemNode(String link, String title) {
        this.link = link;
        this.title = title;
    }

    @Override
    public  String toString()
    {
        return  title;
    }



    public void setLink(String link){
        this.link = link;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getLink(){
        return this.link;
    }

    public String getTitle(){
        return this.title;
    }
}
