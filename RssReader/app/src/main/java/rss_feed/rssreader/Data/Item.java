package rss_feed.rssreader.Data;

import java.util.ArrayList;

/**
 * Created by Phil on 24/04/2016.
 */
public class Item {
    private String link;
    private String title;
    private String email;
    private String password;

    public Item(){
    }

    /**
     *
     * @param link
     * @param title
     */
    public Item(String link, String title){
        this.link = link;
        this.title = title;
    }

    public Item(String link, String title, String email, String password){
        this.link = link;
        this.title = title;
        this.email = email;
        this.password = password;
    }

    void setEmail(String email) {
        this.email = email;
    }

    void setPassword(String password) {
        this.password = password;
    }

    @Override
    public  String toString()
    {
        return  title;
    }

    public String getLink(){
        return link;
    }

    public String getTitle(){return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

}
