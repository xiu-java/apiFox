package com.example.apifox.model;

public class Item {
    private Detail get;
    private Detail post;
    private Detail put;

    public Detail getPut() {
        return put;
    }

    public void setPut(Detail put) {
        this.put = put;
    }

    public Detail getPost() {
        return post;
    }

    public void setPost(Detail post) {
        this.post = post;
    }

    public Detail getGet() {
        return get;
    }

    public void setGet(Detail get) {
        this.get = get;
    }

}
