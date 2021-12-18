package app.web.markodunovic.retrofitexample;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    //base html url gets passed latter
    //this is just an endpoint
    //if i use a querry then it must be the same as what is
    //expected on the html sausage
    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId")Integer[] userId,
            @Query("_sort")String sort,
            @Query("_order")String order

            // can Query like this or with the array as shown above
            /*@Query("userId")int userId,
            @Query("userId")int userId2,*/
    );
    // call object contains a single requiest and a response

    //{} is a replacement id
    //@Path name and replacement id have to be the same
    //what we call the variable does not mater

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id")int postId);

    @GET("posts")
    Call<List<Post>> getPosts2(
            // string for the key (parameter name) & the value
            @QueryMap Map<String,String> parameters
            );

    @GET
    Call<List<Comment>> getComments(@Url String url);
}
