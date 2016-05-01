package rss_feed.rssreader.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import rss_feed.rssreader.Core.DividerItemDecoration;
import rss_feed.rssreader.Core.FluxAdapter;
import rss_feed.rssreader.Data.Flux;
import rss_feed.rssreader.Data.FluxNode;
import rss_feed.rssreader.Data.ListFlux;
import rss_feed.rssreader.Data.RssReaderWebService;
import rss_feed.rssreader.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private List<FluxNode> fluxList = new ArrayList<>();
    private RecyclerView        recyclerView;
    private FluxAdapter         mAdapter;
    protected LayoutManagerType mCurrentLayoutManagerType;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected String             UrlEditText = "Test";
    protected String             TitleEditText = "Test";

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_flux);
        mLayoutManager = new LinearLayoutManager(this);
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState.getSerializable("LayoutManager");
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mAdapter = new FluxAdapter(fluxList, new FluxAdapter.OnItemClickListener(){
            @Override public void onItemClick(FluxNode flux){
                Intent  in = new Intent(MainActivity.this, ListItemActivity.class);
                Bundle  args = new Bundle();

                args.putString("link", mAdapter.getFlux(1).getTitle());
                in.putExtras(args);
                startActivity(in);
            }
        });

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    //    recyclerView.addItemDecoration(new DividerItemDecoration(getParent(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        RssReaderWebService.getInstance().getListFlux(this);
    }

    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getParent());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getParent());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    public void setRecyclerView(ListFlux listFlux) {
        fluxList.clear();
        for (FluxNode flux : listFlux.getList())
            addFluxToList(flux.getTitle(), flux.getLink());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    public void createDialogAddFlux(){
        LinearLayout        layout = new LinearLayout(this.getApplicationContext());
        final EditText      inputUrl = new EditText(this);
        final EditText      inputTitle = new EditText(this);

        layout.setOrientation(LinearLayout.VERTICAL);
        inputUrl.setInputType(InputType.TYPE_CLASS_TEXT);
        inputTitle.setInputType(InputType.TYPE_CLASS_TEXT);
        inputUrl.setHint("Url Channel");
        inputTitle.setHint("Your Title");
        layout.addView(inputTitle);
        layout.addView(inputUrl);
        // Create Dialog Box
        final AlertDialog.Builder   dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Add a new Flux");
        dialogBuilder.setView(layout);

        UrlEditText = inputUrl.toString();
        TitleEditText = inputTitle.toString();
        dialogBuilder.setPositiveButton("Validate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Start ListItemActivity with args
                TitleEditText = inputTitle.getText().toString();
                UrlEditText = inputUrl.getText().toString();
                addFlux(TitleEditText, UrlEditText);

                startListItemActivity();
                // Appel au WebService pour ajouter un flux
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

    public void startListItemActivity() {
        Bundle args = new Bundle();
        args.putString("url", UrlEditText);
        Intent in = new Intent(MainActivity.this, ListItemActivity.class);
        startActivity(in, args);
    }

    protected void addFluxToList(String title, String url) {
        FluxNode       flux = new FluxNode(url, title);

        fluxList.add(flux);
        mAdapter.notifyDataSetChanged();
    }

    protected void addFlux(String title, String url) {
        Flux        flux = new Flux(url, title);
        FluxNode    fluxN = new FluxNode(url, title);

        RssReaderWebService.getInstance().addFlux(flux);
        fluxList.add(fluxN);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_flux) {
            createDialogAddFlux();
        }
        else if (id == R.id.nav_item_saved) {
            startItemSavedActivity();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void     startItemSavedActivity() {
        Intent in = new Intent(MainActivity.this, ListItemSaved.class);
        startActivity(in);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
