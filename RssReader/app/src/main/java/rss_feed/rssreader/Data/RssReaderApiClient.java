package rss_feed.rssreader.Data;

/**
 * Created by Phil on 23/04/2016.
 */
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RssReaderApiClient {
    @POST("login/")
    Call<ResultQuery> getUserLogin(@Body User user);

    @POST("signup/")
    Call<ResultQuery> postUser(@Body User user);

    @POST("addFlux/")
    Call<ResultQuery> addFlux(@Body Flux flux);

    @POST("addItem/")
    Call<ResultQuery> addItem(@Body Item item);

    @POST("getListFlux/")
    Call<ListFlux> getListFlux(@Body User user);

    @POST("getListItem/")
    Call<ListItem>  getListItem(@Body User user);

    @POST("remItem/")
    Call<ResultQuery> remItem(@Body Item item);

    @POST("remFlux/")
    Call<ResultQuery> remFlux(@Body Flux flux);

}
