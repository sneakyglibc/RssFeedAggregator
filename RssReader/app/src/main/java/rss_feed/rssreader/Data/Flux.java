package rss_feed.rssreader.Data;


/**
 * Created by Phil on 24/04/2016.
 */
public class Flux {
    private String      link;
    private String      title;
    private String      email;
    private String      password;

    public Flux(String link, String title) {
        this.link = link;
        this.title = title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle(){
        return this.title;
    }
}
