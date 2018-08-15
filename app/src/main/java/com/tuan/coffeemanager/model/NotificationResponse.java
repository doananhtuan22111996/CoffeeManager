package com.tuan.coffeemanager.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationResponse {

    @SerializedName("message")
    @Expose
    private String message;

    public NotificationResponse(String message) {
        this.message = message;
    }

    public NotificationResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
