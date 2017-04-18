package ru.eva.automessanger.Models;

import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("response")
    private String response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
