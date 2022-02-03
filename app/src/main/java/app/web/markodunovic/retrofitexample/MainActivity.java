package app.web.markodunovic.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private HttpLoggingInterceptor httpLoggingInterceptor =  new HttpLoggingInterceptor();
    private OkHttpClient okHttpClient2 = new OkHttpClient.Builder().
            addInterceptor(httpLoggingInterceptor)
            .build();;
    private TextView textViewResult;
    private FloatingActionButton fab1,fab2,fab3,fab4,fab5PostButton,
            fabPostFormEncoding,fabPostsFieldMap,fab8,fab9,fab10;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient2)
            .build();;
    private JsonPlaceHolderApiPart2 jsonPlaceHolderApiPart2 =
            retrofit2.create(JsonPlaceHolderApiPart2.class);




    //to force gson to serialize nulls use
    // Gson gson = new Gson.Builder().serializeNulls().create();
    // pass the gson to the GsonConverterFactory.create(gson)

    //todo continue latter https://www.youtube.com/watch?v=c1b2HehvL2M part 6 headers

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.text_view_result);
        setInterceptor();
        setButtons();
    }

    private void setInterceptor() {
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add to desired retrofit object i use it on all methods that have interface 2 since its global

    }

    private void setButtons() {
        fab1 = findViewById(R.id.fab_btn_1);
        fab1.setImageBitmap(textAsBitmap("1", 40, Color.WHITE));
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText("");
                doBasicShit();
            }
        });
        fab2 =findViewById(R.id.fab_btn_2);
        fab2.setImageBitmap(textAsBitmap("2",40,Color.WHITE));
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText("");
                getKomentare();

            }
        });
        fab3 = findViewById(R.id.fab_btn_3);
        fab3.setImageBitmap(textAsBitmap("3",40,Color.BLACK));
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText("");
                doBasicShit2();
            }
        });
        fab4 = findViewById(R.id.fab_btn_4);
        fab4.setImageBitmap(textAsBitmap("4",40,Color.WHITE));
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewResult.setText("");
                urlSausage();
            }
        });
        fab5PostButton = findViewById(R.id.fab_btn_5);
        fab5PostButton.setImageBitmap(textAsBitmap("5",40,Color.WHITE));
        fab5PostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postToServer();
            }
        });
        fabPostFormEncoding = findViewById(R.id.fab_btn_6);
        fabPostFormEncoding.setImageBitmap(textAsBitmap("6",40,Color.WHITE));
        fabPostFormEncoding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formEncodingPost();
            }
        });
        fabPostsFieldMap = findViewById(R.id.fab_btn_7);
        fabPostsFieldMap.setImageBitmap(textAsBitmap("7",40,Color.WHITE));
        fabPostsFieldMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postHashMap();
            }
        });
        fab8 = findViewById(R.id.fab_btn_8);
        fab8.setImageBitmap(textAsBitmap("8",40,Color.WHITE));
        fab8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePost();
            }
        });
        fab9 = findViewById(R.id.fab_btn_9);
        fab9.setImageBitmap(textAsBitmap("9",40,Color.WHITE));
        fab9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patchPost();
            }
        });
        fab10 = findViewById(R.id.fab_btn_10);
        fab10.setImageBitmap(textAsBitmap("10",40,Color.WHITE));
        fab10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yeet();
            }
        });
    }

    private void yeet() {

        Call<Void> call = jsonPlaceHolderApiPart2.deletePost(5);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code"+response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText("Code" +t.getMessage());
            }
        });

    }

    private void updatePost() {

        Post post = new Post(12,null,"New Text");
        Call<Post> call = jsonPlaceHolderApiPart2.putPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Http Code: "+response.code() +"\n";
                assert postResponse != null;
                content += "Auto Gen ID: "+postResponse.getId() +"\n";
                content += "User ID: " +postResponse.getUserId() +"\n";
                content += "Title: " + postResponse.getTitle()+"\n";
                content += "Text: " +postResponse.getText()+ "\n\n";
                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }
    private void patchPost() {

        Post post = new Post(12,null,"New Text");
        Call<Post> call = jsonPlaceHolderApiPart2.patchPost(5,post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                content += "Http Code: "+response.code() +"\n";
                assert postResponse != null;
                content += "Auto Gen ID: "+postResponse.getId() +"\n";
                content += "User ID: " +postResponse.getUserId() +"\n";
                content += "Title: " + postResponse.getTitle()+"\n";
                content += "Text: " +postResponse.getText()+ "\n\n";
                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }


    private void postHashMap() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Map<String,String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","new title");

        Call<Post> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Http Code: "+response.code() +"\n";
                assert postResponse != null;
                content += "Auto Gen ID: "+postResponse.getId() +"\n";
                content += "User ID: " +postResponse.getUserId() +"\n";
                content += "Title: " + postResponse.getTitle()+"\n";
                content += "Text: " +postResponse.getText()+ "\n\n";
                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void formEncodingPost() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Call<Post> call = jsonPlaceHolderApi.createPost(23,"new title","new Text");
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Http Code: "+response.code() +"\n";
                assert postResponse != null;
                content += "Auto Gen ID: "+postResponse.getId() +"\n";
                content += "User ID: " +postResponse.getUserId() +"\n";
                content += "Title: " + postResponse.getTitle()+"\n";
                content += "Text: " +postResponse.getText()+ "\n\n";
                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void postToServer() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Post post = new Post(3,"new title","new text");
        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                Post postResponse = response.body();
                String content = "";
                content += "Http Code: "+response.code() +"\n";
                assert postResponse != null;
                content += "Auto Gen ID: "+postResponse.getId() +"\n";
                content += "User ID: " +postResponse.getUserId() +"\n";
                content += "Title: " + postResponse.getTitle()+"\n";
                content += "Text: " +postResponse.getText()+ "\n\n";
                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void urlSausage() {
        //note if the api has multiple versions for example
        //https://jsonplaceholder.typicode.com/v2/
        //it must end with a backslash or it will throw a illegal state exception
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        // its gonna bitch here because it now takes an argument for the {} and path
        //can also pass the full url here example base url + endpoint
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment:comments){
                    String content = "";
                    content += "ID: "+ comment.getId()+"\n";
                    content += "Post ID: "+ comment.getPostId()+"\n";
                    content += "Name: "+ comment.getName()+"\n";
                    content += "Email: "+ comment.getEmail()+"\n";
                    content += "Text: "+ comment.getId()+"\n\n";
                    textViewResult.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }


    private void getKomentare() {
        // there was an error here dont forget to link retrofit and interface or it will be a null exception
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        // its gonna bitch here because it now takes an argument for the {} and path
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(3);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                if (!response.isSuccessful()){
                    textViewResult.setText("Code: "+response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment:comments){
                    String content = "";
                    content += "ID: "+ comment.getId()+"\n";
                    content += "Post ID: "+ comment.getPostId()+"\n";
                    content += "Name: "+ comment.getName()+"\n";
                    content += "Email: "+ comment.getEmail()+"\n";
                    content += "Text: "+ comment.getId()+"\n\n";
                    textViewResult.append(content);

                }

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    //now with maps
    private void doBasicShit2() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Map<String,String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts2(parameters);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts){
                    String content = "";
                    content += "ID: "+post.getId() +"\n";
                    content += "User ID: " +post.getUserId() +"\n";
                    content += "Title: " + post.getTitle()+"\n";
                    content += "Text: " +post.getText()+ "\n\n";
                    Log.d("response", "onResponse: "+content);
                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });
    }


    private void doBasicShit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        // can actually pass null for parameters they will just be ignored
        // except for the integer since its not nullable
        // Integer is nullable int is not nullable because Integer is a wrapper around int
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(new Integer[]{1,3,5},"id","asc");
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (!response.isSuccessful()){
                    textViewResult.setText("Code "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                for (Post post : posts){
                    String content = "";
                    content += "ID: "+post.getId() +"\n";
                    content += "User ID: " +post.getUserId() +"\n";
                    content += "Title: " + post.getTitle()+"\n";
                    content += "Text: " +post.getText()+ "\n\n";
                    Log.d("response", "onResponse: "+content);
                    textViewResult.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
}