package app.web.markodunovic.retrofitexample;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JsonPlaceHolderApiPart2 {


    @PUT("posts/{id}")
    Call<Post> putPost(
            @Path(value = "id")int id,
            @Body Post post
    );

    @PATCH("posts/{id}")
    Call<Post> patchPost(
            @Path(value = "id")int id,
            @Body Post post
    );

    @DELETE("posts/{id}")
    Call<Void> deletePost(
            @Path(value = "id")int id
    );

}
