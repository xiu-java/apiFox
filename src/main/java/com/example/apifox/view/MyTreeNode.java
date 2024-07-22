package com.example.apifox.view;

import javax.swing.tree.DefaultMutableTreeNode;

public class MyTreeNode extends DefaultMutableTreeNode {
    private String column1;
    private String column2;
    private String column3;

    public MyTreeNode(String column1, String column2, String column3) {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
    }

    public String getColumn1() {
        return column1;
    }

    public String getColumn2() {
        return column2;
    }

    public String getColumn3() {
        return column3;
    }
}
