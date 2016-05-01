package rss_feed.rssreader.Data;

import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rss_feed.rssreader.Activity.ListItemSaved;
import rss_feed.rssreader.Activity.MainActivity;

/**
 * Created by Phil on 24/04/2016.
 */
public class RssReaderWebService {
    private static final String TAG = "RssReaderWebService";
    private static final String URL = "http://192.168.0.24:3000";
    public boolean              isConnected = false;
    private RestClient          mClient;
    private ArrayList<Flux> mListFlux;
    private ArrayList<Item>     mListItem;
    private String              email, password;

    private final static RssReaderWebService INSTANCE = new RssReaderWebService();

    public static RssReaderWebService getInstance(){
        return INSTANCE;
    }

    private RssReaderWebService(){
        mClient = new RestClient(URL);
        mListFlux = new ArrayList<>();
        mListItem = new ArrayList<>();
    }

    public void addObjectListFlux(Flux flux)
    {
        mListFlux.add(flux);
    }

    public ArrayList<Flux> getListFlux()
    {
        return mListFlux;
    }

    public void addObjectListItem(Item item)
    {
        mListItem.add(item);
    }

    public ArrayList<Item> getListItem()
    {
        return mListItem;
    }

    public void getUserLogin(final User user)
    {
        Call<ResultQuery> call = mClient.getApiClient().getUserLogin(user);

        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                if (response.isSuccess()) {
                    isConnected = true;
                    email = user.getEmail();
                    password = user.getPassword();
                    Log.v(TAG, "Login Success");
                } else {
                    isConnected = false;
                    Log.v(TAG, "Login :error response, no access to resource?");
                }
            }
            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                isConnected = false;
                Log.v(TAG, "Login on Failure");
            }
        });
    }

    public void postUser(User user)
    {
        Call<ResultQuery> call = mClient.getApiClient().postUser(user);

        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                if (response.isSuccess()) {
                    isConnected = true;
                    Log.v(TAG, "Sign Up Success");
                } else {
                    isConnected = false;
                    Log.v(TAG, "Sign Up: error response, no access to resource?");
                }
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                isConnected = false;
                Log.v(TAG, "SignUp on Failure");
            }
        });
    }

    public void addFlux(Flux flux){
        flux.setEmail(email);
        flux.setPassword(password);
        Call<ResultQuery>  call = mClient.getApiClient().addFlux(flux);

        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                if (response.isSuccess()) {
                    Log.v(TAG, "Add flux Success");

                } else {
                    Log.v(TAG, "Add flux : error response, no access to resource?");
                }
            }

            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Log.v(TAG, "Failure adding Flux");
            }
        });
    }

    public void addItem(Item item){
        item.setEmail(email);
        item.setPassword(password);
        Call<ResultQuery>  call = mClient.getApiClient().addItem(item);

        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                if (response.isSuccess()) {
                    Log.v(TAG, "Add Item Success");
                } else {
                    Log.v(TAG, "Add Item : error response, no access to resource?");
                }
            }
            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Log.v(TAG, "Failure adding Flux");
            }
        });
    }

    public void getListFlux(final MainActivity mainAct){
        User    user = new User(password, email);
        Call<ListFlux> call = mClient.getApiClient().getListFlux(user);

        call.enqueue(new Callback<ListFlux>() {
            @Override
            public void onResponse(Call<ListFlux> call, Response<ListFlux> response) {
                if (response.isSuccess()) {
                    Log.v(TAG, "Get ListFlux Success");
                    Log.v(TAG, "GetList = " + response.body().getList().toString());
                    mainAct.setRecyclerView(response.body());
                } else {
                    Log.v(TAG, "GetListFlux : error response, no access to resource?");
                }
            }
            @Override
            public void onFailure(Call<ListFlux> call, Throwable t) {
                Log.v(TAG, "Failure Getting ListFlux");
            }
        });
    }

    public void getListItem(final ListItemSaved LstSvdAct){
        User    user = new User(password, email);
        Call<ListItem> call = mClient.getApiClient().getListItem(user);

        call.enqueue(new Callback<ListItem>() {
            @Override
            public void onResponse(Call<ListItem> call, Response<ListItem> response) {
                if (response.isSuccess()) {
                    Log.v(TAG, "Get ListItem Success");
                    Log.v(TAG, "GetList = " + response.body().getList().toString());
                    LstSvdAct.setListView(response.body());
                } else {
                    Log.v(TAG, "GetListItem: error response, no access to resource?");
                }
            }
            @Override
            public void onFailure(Call<ListItem> call, Throwable t) {
                Log.v(TAG, "Failure Getting ListItem");
            }
        });
    }

    public void remItem(Item item){
        item.setEmail(email);
        item.setPassword(password);
        Call<ResultQuery>  call = mClient.getApiClient().remItem(item);

        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                if (response.isSuccess()) {
                    Log.v(TAG, "Remove Item Success");
                } else {
                    Log.v(TAG, "Remove Item : error response, no access to resource?");
                }
            }
            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Log.v(TAG, "Failure removing Flux");
            }
        });
    }

    public void remFlux(Flux flux){
        flux.setEmail(email);
        flux.setPassword(password);
        Call<ResultQuery>  call = mClient.getApiClient().remFlux(flux);

        call.enqueue(new Callback<ResultQuery>() {
            @Override
            public void onResponse(Call<ResultQuery> call, Response<ResultQuery> response) {
                if (response.isSuccess()) {
                    Log.v(TAG, "Remove Flux Success");
                } else {
                    Log.v(TAG, "Remove Flux : error response, no access to resource?");
                }
            }
            @Override
            public void onFailure(Call<ResultQuery> call, Throwable t) {
                Log.v(TAG, "Failure removing Flux");
            }
        });
    }


}
