package com.example.apifox.view;

import com.intellij.openapi.util.NlsContexts;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.dualView.TreeTableView;
import com.intellij.ui.treeStructure.treetable.ListTreeTableModelOnColumns;
import com.intellij.ui.treeStructure.treetable.TreeColumnInfo;
import com.intellij.ui.treeStructure.treetable.TreeTable;
import com.intellij.ui.treeStructure.treetable.TreeTableModel;
import com.intellij.util.ui.ColumnInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.util.IconLoader;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.sql.Array;


public class SchemaSection extends JBPanel {
    public static final Icon MyIcon = IconLoader.getIcon("/icons/apifox.svg", SchemaSection.class);
    public static final ColumnInfo<MyTreeNode,String>[] COLUMN_INFOS = new ColumnInfo[]{
            new ColumnInfo<MyTreeNode,String>("File/Function"){
                @Override
                public @NotNull String valueOf(MyTreeNode myTreeNode) {
                    return "1123";
                }

                @Override
                public final Class<TreeTableModel> getColumnClass() {
                    return TreeTableModel.class;
                }

            },
            new ColumnInfo<MyTreeNode,String>("Branch Coverage") {
                @Override
                public @NotNull String valueOf(MyTreeNode o) {
                    return o.getName();
                }
            },
            new ColumnInfo<MyTreeNode,String>("Line/Region Coverage") {
                @Override
                public @NotNull String valueOf(MyTreeNode o) {
                    return o.getValue();
                }

            },

    };
    SchemaSection(){
        // 创建模型
        MyTreeTableModel treeTableModel = new MyTreeTableModel();

// 创建模型数据
        MyTreeNode root = new MyTreeNode("r","kk");
        MyTreeNode child1 = new MyTreeNode("c","l");
        MyTreeNode child2 = new MyTreeNode("c","q");
        root.add(child1);
        root.add(child2);
        JBLabel label = new JBLabel(MyIcon);
        label.setPreferredSize(new Dimension(100,100));
//        label.setBackground(JBColor.BLUE);
        // 创建 TreeTableView 并设置模型
        TreeTableView treeTableView = new TreeTableView(new ListTreeTableModelOnColumns(root,COLUMN_INFOS));
//        treeTableView.setRowSelectionAllowed(true);
        // 你可以自定义列
        treeTableView.setRootVisible(true); // 设置根节点是否可见
        treeTableView.setPreferredSize(new Dimension(400, 100));
        treeTableView.setRowSelectionAllowed(false);
        treeTableView.setCellSelectionEnabled(false);
        add(treeTableView);
        add(label);
    }

    public JComponent getComponent() {
        return this;
    }
}
