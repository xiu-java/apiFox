package com.example.apifox.model;
import com.example.apifox.view.table.interfaces.TreeNode;

import java.util.ArrayList;

public class SchemaItem implements TreeNode {
   public String key;

    public String type;

    public Boolean required = false;

    public String interfaces;

    public String description;

    public SchemaItem(String key,String type,String description,String interfaces){
        this.key = key;
        this.type = type;
        this.interfaces = interfaces;
        this.description = description;
    }

    public SchemaItem(String key,String type,Boolean required,String description,String interfaces){
        this.key = key;
        this.type = type;
        this.required = required;
        this.interfaces = interfaces;
        this.description = description;
    }

    protected boolean isExpanded = false;

    protected ArrayList<SchemaItem> children = new ArrayList<>();
    @Override
    public void collapse() {
        isExpanded = !isExpanded;
    }

    @Override
    public boolean isCollapse() {
        return isExpanded;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    @Override
    public ArrayList<SchemaItem> getChildren() {
        return children;
    }
    public void add(SchemaItem data){
        children.add(data);
    }
    public void addAll(ArrayList<SchemaItem> data){
        children.addAll(data);
    }
}