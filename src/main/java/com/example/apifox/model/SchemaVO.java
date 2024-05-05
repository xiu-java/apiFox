package com.example.apifox.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class SchemaVO {
    private String type;
    private Map<String,Filed> properties;
    @SerializedName("x-apifox-orders")
    private List<String> xApifoxOrders;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Filed> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Filed> properties) {
        this.properties = properties;
    }

    public List<String> getxApifoxOrders() {
        return xApifoxOrders;
    }

    public void setxApifoxOrders(List<String> xApifoxOrders) {
        this.xApifoxOrders = xApifoxOrders;
    }
}
