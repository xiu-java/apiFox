package com.example.apifox.model;


import com.google.protobuf.Any;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree{
    private Info info;
    private String openapi;
    private Tag[] tags;
    private Map<String, Item> paths = new HashMap<>();
    private Components components;
    private List<Any> servers;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public String getOpenapi() {
        return openapi;
    }

    public void setOpenapi(String openapi) {
        this.openapi = openapi;
    }

    public Tag[] getTags() {
        return tags;
    }

    public void setTags(Tag[] tags) {
        this.tags = tags;
    }

    public Map<String, Item> getPaths() {
        return paths;
    }

    public void setPaths(Map<String, Item> paths) {
        this.paths = paths;
    }

    public Components getComponents() {
        return components;
    }

    public void setComponents(Components components) {
        this.components = components;
    }

    public List<Any> getServers() {
        return servers;
    }

    public void setServers(List<Any> servers) {
        this.servers = servers;
    }
}