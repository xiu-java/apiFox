package com.example.apifox.view;

import com.example.apifox.model.*;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class SourcePanel extends JTree {
    public DefaultTreeModel model = new DefaultTreeModel(null);

    public SourcePanel() {
        setFocusable(false);
        setModel(model);
        setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                Object userObject = node.getUserObject();
                JPanel panel = new JPanel(new BorderLayout());
                panel.setOpaque(true);
                if (userObject instanceof TreeItemVO treeItem) {
                    if (treeItem.isDirectory()) {
                        JLabel iconLabel = new JLabel();
                        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/folder.png")));
                        Image scaledImage = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                        Icon scaledIcon = new ImageIcon(scaledImage);
                        iconLabel.setIcon(scaledIcon);
                        panel.add(iconLabel, BorderLayout.WEST);
                        panel.setPreferredSize(new Dimension(200, 32));
                    }else {
                        JLabel methodLabel = new JLabel(treeItem.getNode().getMethod().getValue());
                        methodLabel.setBorder(JBUI.Borders.emptyLeft(5));
                        Font font = new Font("Arial", Font.BOLD, 12);
                        methodLabel.setPreferredSize(new Dimension(40, methodLabel.getPreferredSize().height));
                        methodLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平居中
                        methodLabel.setVerticalAlignment(SwingConstants.CENTER);
                        methodLabel.setFont(font);
                        switch (treeItem.getNode().getMethod()){
                            case GET -> {
                                methodLabel.setForeground(JBColor.GREEN);
                            }
                            case POST -> {
                                methodLabel.setForeground(JBColor.ORANGE);
                            }
                            case PUT -> {
                                methodLabel.setForeground(JBColor.BLUE);
                            }
                            case DELETE -> {
                                methodLabel.setForeground(JBColor.RED);
                            }
                        }
                        panel.add(methodLabel, BorderLayout.WEST);
                        panel.setPreferredSize(new Dimension(200, 25));
                    }
                    JLabel textLabel = new JLabel( treeItem.getTitle());
                    textLabel.setBorder(JBUI.Borders.emptyLeft(5));
                    panel.add(textLabel, BorderLayout.CENTER);
                }
                return panel;
            }
        });
        setRowHeight(0);
    }





    public void updateUi(Tree list) {
        TreeItemVO rootNode = new TreeItemVO();
        rootNode.setRoot(true);
        rootNode.setDirectory(true);
        rootNode.setTitle("Root");
        rootNode.setChildren(new ArrayList<>());
        DefaultMutableTreeNode rootItem = new DefaultMutableTreeNode(rootNode);
        treeModel = new DefaultTreeModel(rootItem);
        for (Tag tag:list.getTags()){
            String tagName = tag.getName();
            if(tagName.equals("deprecated")){
                continue;
            }
            if (tagName.contains("/")) {
                TreeItemVO treeItem  = rootNode;
                String[] items = tagName.split("/");
                for (String item: items) {
                    Optional<TreeItemVO> tree = treeItem.getChildren().stream().filter(t->t.getTitle().equals(item)).findFirst();
                    if(tree.isPresent()){
                        treeItem = tree.get();
                    }else{
                        TreeItemVO node = new TreeItemVO();
                        node.setTitle(item);
                        node.setDirectory(true);
                        node.setChildren(new ArrayList<>());
                        DefaultMutableTreeNode folderItem = new DefaultMutableTreeNode(node);
                        node.setTreeNode(folderItem);
                        treeItem.getChildren().add(node);
                        treeItem.getTreeNode().add(folderItem);
                        treeItem = node;
                    }
                }
            }else{
                TreeItemVO node = new TreeItemVO();
                node.setTitle(tagName);
                node.setDirectory(true);
                node.setChildren(new ArrayList<>());
                DefaultMutableTreeNode folderItem = new DefaultMutableTreeNode(node);
                rootItem.add(folderItem);
                node.setTreeNode(folderItem);
                rootNode.getChildren().add(node);
            }
        }
        for (Map.Entry<String, Item> entry : list.getPaths().entrySet()) {
            String path = entry.getKey();
            Item details = entry.getValue();
            for (Detail detail:details){
                detail.setUrl(path);
                String tag = detail.getTags().isEmpty() ? "root" : detail.getTags().get(0);
                Optional<TreeItemVO> item = rootNode.getChildren().stream().filter(v->v.getTitle().equals(tag)).findFirst();
                if (item.isPresent()) {
                    TreeItemVO node = new TreeItemVO();
                    node.setTitle(detail.getSummary());
                    node.setDirectory(false);
                    node.setNode(detail);
                    item.get().getChildren().add(node);
                    DefaultMutableTreeNode fileItem = new DefaultMutableTreeNode(node);
                    node.setTreeNode(fileItem);
                    item.get().getTreeNode().add(fileItem);
                } else {
                    if(tag.contains("/")){
                        String[] items = tag.split("/");
                        TreeItemVO treeNode = rootNode;
                        for(String element:items){
                            Optional<TreeItemVO> tagItem = treeNode.getChildren().stream().filter(v->v.getTitle().equals(element)).findFirst();
                            if(tagItem.isPresent()){
                                treeNode = tagItem.get();
                            }else {
                                break;
                            }
                        }
                        TreeItemVO node = new TreeItemVO();
                        node.setTitle(detail.getSummary());
                        node.setDirectory(false);
                        node.setNode(detail);
                        DefaultMutableTreeNode fileItem = new DefaultMutableTreeNode(node);
                        treeNode.getTreeNode().add(fileItem);
                        node.setTreeNode(fileItem);
                        treeNode.getChildren().add(node);
                    }else{
                        TreeItemVO node = new TreeItemVO();
                        node.setTitle(detail.getSummary());
                        node.setDirectory(false);
                        node.setNode(detail);
                        DefaultMutableTreeNode fileItem = new DefaultMutableTreeNode(node);
                        node.setTreeNode(fileItem);
                        rootItem.add(fileItem);
                    }
                }
            }
        }
        model.setRoot(rootItem);
    }
}
