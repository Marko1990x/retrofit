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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private FloatingActionButton fab1,fab2,fab3,fab4;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewResult = findViewById(R.id.text_view_result);
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