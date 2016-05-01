package rss_feed.rssreader.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import rss_feed.rssreader.Data.FluxNode;
import rss_feed.rssreader.Data.Item;
import rss_feed.rssreader.Data.ItemNode;
import rss_feed.rssreader.Data.ListItem;
import rss_feed.rssreader.Data.RssReaderWebService;
import rss_feed.rssreader.R;

/**
 * Created by Phil on 01/05/2016.
 */
public class ListItemSaved extends AppCompatActivity {
    ListView mListItem;
    List<ItemNode> mList;
    ArrayAdapter<ItemNode> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_saved);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);

        // Init List View
        mListItem = (ListView) findViewById(R.id.listItemSaved);

        // Init List Note
        mList = new ArrayList<>();
        mAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mList);
        mListItem.setAdapter(mAdapter);
        mListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(mAdapter.getItem(position).getLink()));

                startActivity(i);

            }
        });

        mListItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {
                Item item = new Item(mAdapter.getItem(index).getLink(), mAdapter.getItem(index).getTitle());
                createRemoveItemDialog(item);
                return false;
            }
        });

        //

        RssReaderWebService.getInstance().getListItem(ListItemSaved.this);
    }

    public void setListView(ListItem listItem)
    {
        mAdapter.clear();
        for (ItemNode item : listItem.getList())
            mAdapter.add(item);
        mAdapter.notifyDataSetChanged();
    }

    protected void createRemoveItemDialog(final Item item)
    {
        final AlertDialog.Builder   dialogBuilder = new AlertDialog.Builder(ListItemSaved.this, R.style.MyAlertDialogStyle);
        dialogBuilder.setTitle("Remove Item ?");

        dialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RssReaderWebService.getInstance().remItem(item);
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialogPrice = dialogBuilder.create();
        dialogPrice.show();
    }
}
