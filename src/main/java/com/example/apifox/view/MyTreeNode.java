package com.example.apifox.view;

import javax.swing.tree.DefaultMutableTreeNode;

public class MyTreeNode extends DefaultMutableTreeNode {
    private String name;
    private String value;

    public MyTreeNode(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name; // 在树中显示的节点名称
    }

    public String getValue() {
        return value; // 可以在表格中显示的其他信息
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}