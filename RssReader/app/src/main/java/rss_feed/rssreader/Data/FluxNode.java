package rss_feed.rssreader.Data;

/**
 * Created by Phil on 24/04/2016.
 */
public class FluxNode {
    private String      link;
    private String      title;

    public FluxNode(String link, String title) {
        this.link = link;
        this.title = title;
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
