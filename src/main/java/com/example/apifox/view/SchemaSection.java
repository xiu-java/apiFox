package com.example.apifox.view;

import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import com.intellij.util.ui.ColumnInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;


public class SchemaSection extends JBPanel {

    SchemaSection(){
        MyTreeNode root = new MyTreeNode("Root", "Root Column 2", "Root Column 3");
        MyTreeNode child1 = new MyTreeNode("Child 1", "Child 1 Column 2", "Child 1 Column 3");
        MyTreeNode child2 = new MyTreeNode("Child 2", "Child 2 Column 2", "Child 2 Column 3");

        root.add(child1);
        root.add(child2);

        ColumnInfo<MyTreeNode,String>[] columns = new ColumnInfo[] {
                new ColumnInfo<MyTreeNode,String>("Column 1") {

                    @Override
                    public @NotNull String valueOf(MyTreeNode myTreeNode) {
                        System.out.print(myTreeNode);
                        return myTreeNode.getColumn1();
                    }

                    @Override
                    public @NlsContexts.ColumnName String getName() {
                        return "kkk";
                    }
                },
                new ColumnInfo<MyTreeNode,String>("Column 2") {

                    @Override
                    public @NotNull String valueOf(MyTreeNode myTreeNode) {
                        return myTreeNode.getColumn2();
                    }
                },
                new ColumnInfo<MyTreeNode,String>("Column 3") {
                    @Override
                    public @NotNull String valueOf(MyTreeNode myTreeNode) {
                        return myTreeNode.getColumn3();
                    }
                }
        };
        ListTreeTableModelOnColumns model = new ListTreeTableModelOnColumns(root, columns);
        TreeTable treeTable = new TreeTable(model);
        treeTable.setPreferredSize(new Dimension(400, 400));
        treeTable.setRootVisible(true); // 设置根节点是否可见
        add(treeTable);
    }

    public JComponent getComponent() {
        return this;
    }
}
