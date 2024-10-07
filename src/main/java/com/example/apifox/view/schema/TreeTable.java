package com.example.apifox.view.schema;

import com.example.apifox.model.MethodType;
import com.example.apifox.model.SchemaItem;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.OpenAPI;
import com.example.apifox.model.openapi.v3.models.Operation;
import com.example.apifox.model.openapi.v3.models.PathItem;
import com.example.apifox.model.openapi.v3.models.tags.Tag;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ComponentListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static com.intellij.openapi.util.NullUtils.notNull;

public class TreeTable extends JTree {
    public DefaultTreeModel model = new DefaultTreeModel(null);


    public TreeTable() {
        configUi();
    }

    public TreeTable(SchemaItem data) {
        configUi();
        update(data);
    }

    public void  configUi(){
        setFocusable(false);
        setModel(model);
        setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                Object userObject = node.getUserObject();
                Box panel = new Box(BoxLayout.X_AXIS);
                panel.setOpaque(true);
                if (userObject instanceof SchemaItem treeItem) {
                  panel.add(new JBLabel(treeItem.key));
                  panel.add(new JBLabel(String.format("(%s)", treeItem.type)));
                  if(notNull(treeItem.description)){
                      JBLabel description = new JBLabel(treeItem.description);
                      description.setForeground(JBColor.GRAY);
                      panel.add(description);
                  }
                }
                panel.setPreferredSize(new Dimension(200, 32));
                return panel;
            }
        });
        setRowHeight(0);
    }




    public void update(SchemaItem data){
        DefaultMutableTreeNode rootItem = new DefaultMutableTreeNode(new SchemaItem("root","object","","object"));
         if (data.hasChildren()){
             addRow(rootItem,data);
         }
         model.setRoot(rootItem);
    }

    private void addRow(DefaultMutableTreeNode parent, SchemaItem data) {
        data.getChildren().forEach(item -> {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(item);
            parent.add(node);
            if(item.hasChildren()){
                addRow(node,item);
            }
        });
    }


}


