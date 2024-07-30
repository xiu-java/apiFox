package com.example.apifox.view;

import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreePath;

public class MyTreeTableModel implements TreeTableModel {

    private MyTreeNode root;

    public MyTreeTableModel() {
        root = createTreeNodes(); // 创建树的根节点和子节点
    }

    private MyTreeNode createTreeNodes() {
        MyTreeNode rootNode = new MyTreeNode("Root", "");

        MyTreeNode child1 = new MyTreeNode("Child 1", "Value 1");
        MyTreeNode child2 = new MyTreeNode("Child 2", "Value 2");

        rootNode.add(child1);
        rootNode.add(child2);

        return rootNode; // 返回树结构
    }

    @Override
    public Object getValueAt(Object node, int column) {
        if (node instanceof MyTreeNode) {
            MyTreeNode treeNode = (MyTreeNode) node;
            if (column == 0) {
                return treeNode.toString(); // 第一列是节点名称
            } else {
                return treeNode.getValue(); // 第二列是节点的值
            }
        }
        return null;
    }

    @Override
    public boolean isCellEditable(Object node, int column) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, Object node, int column) {

    }

    @Override
    public void setTree(JTree tree) {

    }

    @Override
    public int getColumnCount() {
        return 2; // 列数
    }

    @Override
    public @NlsContexts.ColumnName String getColumnName(int column) {
        return "";
    }

    @Override
    public Class getColumnClass(int column) {
        return null;
    }

    @Override
    public Object getChild(Object parent, int index) {
        return ((MyTreeNode) parent).getChildAt(index); // 获取子节点
    }

    @Override
    public int getChildCount(Object parent) {
        return ((MyTreeNode) parent).getChildCount(); // 获取子节点数量
    }

    @Override
    public Object getRoot() {
        return root; // 获取根节点
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((MyTreeNode) node).isLeaf(); // 判断是否为叶子节点
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {

    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((MyTreeNode) parent).getIndex((MyTreeNode) child); // 获取子节点索引
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {

    }
}
