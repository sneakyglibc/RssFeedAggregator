package rss_feed.rssreader.Data;

/**
 * Created by Phil on 01/05/2016.
 */
public class ResultQuery {
    private String res;

    public ResultQuery(String res) {
        this.res = res;
    }

    @Override
    public String toString() {return res;}

    public String getRes() {return res;}
}
