package app.web.markodunovic.retrofitexample;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {

    //base html url gets passed latter
    //this is just an endpoint
    @GET("posts")
    Call<List<Post>> getPosts();
    // call object contains a single requiest and a response

}
