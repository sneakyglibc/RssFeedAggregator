package rss_feed.rssreader.Data;

import java.util.List;

/**
 * Created by Phil on 30/04/2016.
 */
public class ListItem {
    private List<ItemNode> data;

    public List<ItemNode> getList() {return data;}

    public void setList(List<ItemNode> list) {this.data = list;}
}
