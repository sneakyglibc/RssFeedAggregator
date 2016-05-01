package rss_feed.rssreader.Data;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.realm.RealmObject;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rss_feed.rssreader.Core.RssReader;

/**
 * Created by Phil on 23/04/2016.
 */
public class RestClient {
    private RssReaderApiClient apiClient;
    private String host;

    private Gson realmGson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            return f.getDeclaringClass().equals(RealmObject.class);
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return false;
        }
    }).create();



    private void rebuildClient() {

/*
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
// add your other interceptors …

// add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!*/


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.host)
                .addConverterFactory(GsonConverterFactory.create(realmGson))
                //.client(httpClient.build())
                .build();

        apiClient = retrofit.create(RssReaderApiClient.class);
    }

    public RestClient(String host) {
        this.host = host;
        rebuildClient();
    }

    public RssReaderApiClient getApiClient() {
        return apiClient;
    }
}
