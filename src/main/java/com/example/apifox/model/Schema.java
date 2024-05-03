package com.example.apifox.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class Schema {
    private String type;

    private Map<String,Filed> properties;

    private List<String> required;

    private String $ref;

    @SerializedName("default")
    private String flied;

    @SerializedName("x-apifox-orders")
    private List<String> xApifoxOrders;

    private String description;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFlied() {
        return flied;
    }

    public void setFlied(String flied) {
        this.flied = flied;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String get$ref() {
        return $ref;
    }

    public void set$ref(String $ref) {
        this.$ref = $ref;
    }

    public Map<String, Filed> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Filed> properties) {
        this.properties = properties;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public List<String> getxApifoxOrders() {
        return xApifoxOrders;
    }

    public void setxApifoxOrders(List<String> xApifoxOrders) {
        this.xApifoxOrders = xApifoxOrders;
    }
}
