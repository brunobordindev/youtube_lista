package br.com.youtube.api;

import br.com.youtube.model.Resultado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeService {

    /*

    https://www.googleapis.com/youtube/v3/
    search
    ?part=snippet
    &order=date
    &maxResults=20
    &key=AIzaSyDcYzasP83qXxPNvKgjZG2xiEOYJA8zXbI
    &channelId=UCVHFbqXqoYvEWM1Ddxl0QDg
    &q=

    https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&maxResults=20&key=AIzaSyDcYzasP83qXxPNvKgjZG2xiEOYJA8zXbI&channelId=UCVHFbqXqoYvEWM1Ddxl0QDg

     */

    @GET("search")
    Call<Resultado> recuperarVideos(
            @Query("part") String part,
            @Query("order") String order,
            @Query("maxResults") String maxResults,
            @Query("key") String key,
            @Query("channelId") String channelId,
            @Query("q") String q
    );
}
