package com.example.apifox.model;


import java.util.List;

public class TreeItemVO {
    private String title;
    private boolean isDirectory = false;
    private boolean isRoot = false;
    private TreeNode node;
    private List<TreeItemVO> children;

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

    public TreeNode getNode() {
        return node;
    }

    public void setNode(TreeNode node) {
        this.node = node;
    }

    public List<TreeItemVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeItemVO> children) {
        this.children = children;
    }
}