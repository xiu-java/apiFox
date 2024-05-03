package com.example.apifox.model;


public class Filed {
    private String type;
    private String title;
    private  ChildrenFiled items;
    private String description;
    private Boolean nullable;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ChildrenFiled getItems() {
        return items;
    }

    public void setItems(ChildrenFiled items) {
        this.items = items;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getNullable() {
        return nullable;
    }

    public void setNullable(Boolean nullable) {
        this.nullable = nullable;
    }
}
