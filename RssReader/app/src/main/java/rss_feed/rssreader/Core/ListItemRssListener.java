package rss_feed.rssreader.Core;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import rss_feed.rssreader.Data.Item;

public class ListItemRssListener implements OnItemClickListener {

    // List item's reference
    List<Item> listItems;
    // Calling activity reference
    Activity activity;

    public ListItemRssListener(List<Item> aListItems, Activity anActivity) {
        listItems = aListItems;
        activity  = anActivity;
    }

    /**
     * Start a browser with url from the rss item.
     */
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(listItems.get(pos).getLink()));

        activity.startActivity(i);

    }
}
