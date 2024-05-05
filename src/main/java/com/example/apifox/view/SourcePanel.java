package com.example.apifox.view;

import com.example.apifox.component.DataSourceService;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.TreeNode;
import com.intellij.openapi.project.ProjectManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/folder.png"));
                        Image scaledImage = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                        Icon scaledIcon = new ImageIcon(scaledImage);
                        iconLabel.setIcon(scaledIcon);
                        panel.add(iconLabel, BorderLayout.WEST);
                        panel.setPreferredSize(new Dimension(200, 32));
                    }else {
                        JLabel methodLabel = new JLabel(treeItem.getNode().getMethod().getValue());
                        methodLabel.setBorder(new EmptyBorder(0, 5, 0, 0));
                        Font font = new Font("Arial", Font.BOLD, 12);
                        methodLabel.setPreferredSize(new Dimension(30, methodLabel.getPreferredSize().height));
                        methodLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平居中
                        methodLabel.setVerticalAlignment(SwingConstants.CENTER);
                        methodLabel.setFont(font);
                        switch (treeItem.getNode().getMethod()){
                            case GET -> {
                                methodLabel.setForeground(Color.green);
                            }
                            case POST -> {
                                methodLabel.setForeground(Color.ORANGE);
                            }
                            case PUT -> {
                                methodLabel.setForeground(Color.blue);
                            }
                            case DELETE -> {
                                methodLabel.setForeground(Color.red);
                            }
                        }
                        panel.add(methodLabel, BorderLayout.WEST);
                        panel.setPreferredSize(new Dimension(200, 25));
                    }
                    JLabel textLabel = new JLabel( treeItem.getTitle());
                    textLabel.setBorder(new EmptyBorder(0, 5, 0, 0));
                    panel.add(textLabel, BorderLayout.CENTER);
                }
                return panel;
            }
        });
        setRowHeight(0);
        configUi();
    }



    private void configUi() {
        DataSourceService service = ProjectManager.getInstance().getDefaultProject().getService(DataSourceService.class);
        Map<String, List<TreeNode>> list = service.getDataSource();
        TreeItemVO rootNode = new TreeItemVO();
        rootNode.setRoot(true);
        rootNode.setDirectory(true);
        rootNode.setTitle("Root");
        rootNode.setChildren(new ArrayList<>());
        DefaultMutableTreeNode rootItem = new DefaultMutableTreeNode(rootNode);
        treeModel = new DefaultTreeModel(rootItem);
        for (Map.Entry<String, List<TreeNode>> entry : list.entrySet()) {
            String folder = entry.getKey();
            List<TreeNode> data = entry.getValue();
            TreeItemVO folderNodeVO = new TreeItemVO();
            folderNodeVO.setTitle(folder);
            folderNodeVO.setDirectory(true);
            folderNodeVO.setChildren(new ArrayList<>());
            rootNode.getChildren().add(folderNodeVO);
            DefaultMutableTreeNode folderItem = new DefaultMutableTreeNode(folderNodeVO);
            rootItem.add(folderItem);
            for (TreeNode treeNode : data) {
                TreeItemVO fileNodeVO = new TreeItemVO();
                fileNodeVO.setTitle(treeNode.getSummary());
                fileNodeVO.setNode(treeNode);
                folderNodeVO.getChildren().add(fileNodeVO);
                DefaultMutableTreeNode fileItem = new DefaultMutableTreeNode(fileNodeVO);
                folderItem.add(fileItem);
            }
        }
        model.setRoot(rootItem);
    }
}
