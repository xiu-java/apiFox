package com.example.apifox.model;


import com.example.apifox.model.openapi.v3.models.Operation;
import com.example.apifox.model.openapi.v3.models.Components;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.List;

public class TreeItemVO {
    private MethodType method;
    private String title;
    private String url;
    public  Boolean completed = false;
    private boolean isDirectory = false;
    private boolean isRoot = false;
    public SchemaItem query = null;
    public SchemaItem path = null;
    public SchemaItem body = null;
    public SchemaItem response = null;
    private Operation node;
    private DefaultMutableTreeNode treeNode;
    private List<TreeItemVO> children;
    public static Components components;

    public MethodType getMethod() {
        return method;
    }

    public void setMethod(MethodType methodType) {
        this.method = methodType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public Operation getNode() {
        return node;
    }

    public void setNode(Operation node) {
        this.node = node;
    }

    public DefaultMutableTreeNode getTreeNode() {
        return treeNode;
    }

    public void setTreeNode(DefaultMutableTreeNode treeNode) {
        this.treeNode = treeNode;
    }

    public List<TreeItemVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeItemVO> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean hasChildren(){
        return this.children != null && this.children.size() > 0;
    }
}