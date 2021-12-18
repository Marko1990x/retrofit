package app.web.markodunovic.retrofitexample;

import com.google.gson.annotations.SerializedName;

public class Post {

    private int userId;
    private Integer id; // gson ignores all fields that are null
    // Integer is nullable int (primitive type) isnt
    private String title;
    @SerializedName("body") // if the name on the api is different then here so it knows how to link them
    private String text;

    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

}
