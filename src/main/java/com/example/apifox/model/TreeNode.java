package com.example.apifox.model;

public class TreeNode  extends Detail{
    private String url;
    private MethodType method;

    public MethodType getMethod() {
        return method;
    }

    public void setMethod(MethodType method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
