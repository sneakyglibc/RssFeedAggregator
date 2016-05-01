package rss_feed.rssreader.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.AppCompatButton;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import rss_feed.rssreader.Core.ListItemRssListener;
import rss_feed.rssreader.Core.RssReader;
import rss_feed.rssreader.Data.Item;
import rss_feed.rssreader.Data.ListItem;
import rss_feed.rssreader.Data.RssReaderWebService;
import rss_feed.rssreader.R;

/**
 * Created by Phil on 30/04/2016.
 */
public class ListItemActivity extends Activity {
    // A reference to the local object
    private ListItemActivity local;
    private String            link;

    /**
     * This method creates main application view
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set view
        setContentView(R.layout.list_item);

        // Set reference to this activity
        local = this;

        Bundle args = getIntent().getExtras();

        link = args.getString("link");
        GetRSSDataTask task = new GetRSSDataTask();
        // Start download RSS task
        task.execute(link);

        // Debug the thread name
        Log.d("RssReader", Thread.currentThread().getName());
    }

    private class GetRSSDataTask extends AsyncTask<String, Void, List<Item> > {
        private ArrayAdapter<Item> adapter;
        @Override
        protected List<Item> doInBackground(String... urls) {

            // Debug the task thread name
            Log.d("RssReader", Thread.currentThread().getName());

            try {
                // Create RSS reader
                RssReader rssReader = new RssReader(urls[0]);

                // Parse RSS, get items
                return rssReader.getItems();

            } catch (Exception e) {
                Log.e("RssReader", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Item> result) {

            // Get a ListView from main view
            final ListView Items = (ListView) findViewById(R.id.listItemView);

            // Create a list adapter
            Items.setBackgroundColor(Color.BLUE);
            adapter = new ArrayAdapter<Item>(local.getApplicationContext(),android.R.layout.simple_list_item_1, result);
            // Set list adapter for the ListView
            Items.setAdapter(adapter);
            Items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                               int index, long arg3) {

                    createChoiceItemDialog(adapter.getItem(index));
                    return false;
                }
            });

            Items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(adapter.getItem(position).getLink()));

                    startActivity(i);

                }
            });
            // Set list view item click listener
            Items.setOnItemClickListener(new ListItemRssListener(result, local));
        }

        protected void createChoiceItemDialog(final Item item) {
            // Create Dialog Box
            final AlertDialog.Builder   dialogBuilder = new AlertDialog.Builder(ListItemActivity.this, R.style.MyAlertDialogStyle);
            dialogBuilder.setTitle("Save Item ?");

            dialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RssReaderWebService.getInstance().addItem(item);
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
}
