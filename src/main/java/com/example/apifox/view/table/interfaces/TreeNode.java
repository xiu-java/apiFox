package com.example.apifox.view.table.interfaces;

import com.example.apifox.model.SchemaItem;

import java.util.ArrayList;

public interface TreeNode {
    ArrayList<Integer> index = new ArrayList<>();

    boolean isExpanded = false;

    ArrayList<TreeNode> children = null;

    void collapse();

    boolean isCollapse();

    boolean hasChildren();

    ArrayList<SchemaItem> getChildren();
}
