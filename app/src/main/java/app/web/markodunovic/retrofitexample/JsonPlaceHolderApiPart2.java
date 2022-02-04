package app.web.markodunovic.retrofitexample;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApiPart2 {


    @Headers({"Static-Header1: 123","Static-Header2: 456"})
    @PUT("posts/{id}")
    Call<Post> putPost(
            // if i want to pass a value to header later on
            @Header("Dynamic-Header")String header,
            @Path(value = "id")int id,
            @Body Post post
    );

    @PATCH("posts/{id}")
    Call<Post> patchPost(
            @HeaderMap Map<String,String> headers,
            @Path(value = "id")int id,
            @Body Post post
    );

    @DELETE("posts/{id}")
    Call<Void> deletePost(
            @Path(value = "id")int id
    );

}
