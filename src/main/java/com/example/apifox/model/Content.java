package com.example.apifox.model;

import com.google.gson.annotations.SerializedName;

public class Content {
    @SerializedName("application/json")
    public Body body;

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
