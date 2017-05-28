package ru.eva.automessanger.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Vladimir on 29.05.2017.
 */

public class Response {
    @SerializedName("likes")
    private int likes;

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
