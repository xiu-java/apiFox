package com.example.apifox.view;

import com.example.apifox.model.MethodType;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.OpenAPI;
import com.example.apifox.model.openapi.v3.models.Operation;
import com.example.apifox.model.openapi.v3.models.PathItem;
import com.example.apifox.model.openapi.v3.models.tags.Tag;
import com.example.apifox.utils.FileOperation;
import com.example.apifox.utils.SchemaData;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellEditor;
import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.intellij.openapi.util.NullUtils.notNull;

public class SourcePanel extends JTree {
    public DefaultTreeModel model = new DefaultTreeModel(null);


    public SourcePanel() {
        setFocusable(false);
        setModel(model);
        setEditable(true);
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
                        JLabel textLabel = new JLabel( treeItem.getTitle());
                        textLabel.setBorder(JBUI.Borders.emptyLeft(5));
                        panel.add(textLabel, BorderLayout.CENTER);
                    }else {
                        JLabel methodLabel = new JLabel(treeItem.getMethod().getValue());
                        methodLabel.setBorder(JBUI.Borders.emptyLeft(5));
                        Font font = new Font("Arial", Font.BOLD, 12);
                        methodLabel.setPreferredSize(new Dimension(40, methodLabel.getPreferredSize().height));
                        methodLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平居中
                        methodLabel.setVerticalAlignment(SwingConstants.CENTER);
                        methodLabel.setFont(font);
                        switch (treeItem.getMethod()){
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
                        JLabel textLabel = new JLabel( treeItem.getTitle());
                        textLabel.setBorder(JBUI.Borders.emptyLeft(5));
                        panel.add(textLabel, BorderLayout.CENTER);
                    }

                }
                return panel;
            }
        });
        setCellEditor(new TreeCellEditorWithButton());
        setRowHeight(0);
    }



    // 自定义编辑器
    static class TreeCellEditorWithButton extends AbstractCellEditor implements TreeCellEditor {
        private Object currentNode;


        @Override
        public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded,
                                                    boolean leaf, int row) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();
            JPanel panel = new JPanel(new BorderLayout());
            FileOperation instance = new FileOperation();
            SchemaData schemaData = new SchemaData();
            panel.setOpaque(false);
            panel.setPreferredSize(new Dimension(tree.getWidth()-40,  32));
            if (userObject instanceof TreeItemVO treeItem) {
                if (treeItem.isDirectory()) {
                    JLabel iconLabel = new JLabel();
                    ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/folder.png")));
                    Image scaledImage = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                    Icon scaledIcon = new ImageIcon(scaledImage);
                    iconLabel.setIcon(scaledIcon);
                    panel.add(iconLabel, BorderLayout.WEST);
                    JLabel textLabel = new JLabel( treeItem.getTitle());
                    textLabel.setBorder(JBUI.Borders.emptyLeft(5));
                    panel.add(textLabel, BorderLayout.CENTER);
                    ImageIcon createIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/apifox.png")));
                    Image createImage = createIcon.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
                    Icon createScaledIcon = new ImageIcon(createImage);
                    JButton createButton = new JButton(createScaledIcon);
                    createButton.setFocusable(false);
                    createButton.setBorderPainted(false); // 不绘制边框
                    createButton.setContentAreaFilled(false); // 不填充背景颜色
                    createButton.setBorder(JBUI.Borders.empty());
                    createButton.setPreferredSize(new Dimension(24,24));
                    createButton.addActionListener(e -> {
                            DataContext dataContext = DataManager.getInstance().getDataContext();
                            Project project = PlatformDataKeys.PROJECT.getData(dataContext);
                            if(treeItem.hasChildren()){
                                treeItem.getChildren().forEach(item->{
                                    schemaData.collectSchema(item.getNode(),item);
                                });
                                treeItem.completed = true;
                            }

                            instance.createApi(project,treeItem);
                    });
                    panel.add(createButton, BorderLayout.EAST);
                }else {
                    JLabel methodLabel = new JLabel(treeItem.getMethod().getValue());
                    methodLabel.setBorder(JBUI.Borders.emptyLeft(5));
                    Font font = new Font("Arial", Font.BOLD, 12);
                    methodLabel.setPreferredSize(new Dimension(40, methodLabel.getPreferredSize().height));
                    methodLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平居中
                    methodLabel.setVerticalAlignment(SwingConstants.CENTER);
                    methodLabel.setFont(font);
                    switch (treeItem.getMethod()){
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
                    JLabel textLabel = new JLabel( treeItem.getTitle());
                    textLabel.setBorder(JBUI.Borders.emptyLeft(5));
                    panel.add(textLabel, BorderLayout.CENTER);
                }

            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return currentNode;
        }
    }

    public void updateUi(OpenAPI data) {
        TreeItemVO rootNode = new TreeItemVO();
        rootNode.setRoot(true);
        rootNode.setDirectory(true);
        rootNode.setTitle("Root");
        rootNode.setChildren(new ArrayList<>());
        DefaultMutableTreeNode rootItem = new DefaultMutableTreeNode(rootNode);
        for (Tag tag:data.getTags()){
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
        for (Map.Entry<String, PathItem> entry : data.getPaths().entrySet()) {
            String path = entry.getKey();
            PathItem pathItem = entry.getValue();
            if (notNull(pathItem.getGet())){
                addFile(pathItem.getGet(), rootNode, rootItem,MethodType.fromString("get"),path);
            }
            if (notNull(pathItem.getPost())){
                addFile(pathItem.getPost(), rootNode, rootItem,MethodType.fromString("post"),path);
            }
            if (notNull(pathItem.getPut())){
                addFile(pathItem.getPut(), rootNode, rootItem,MethodType.fromString("put"),path);
            }
            if (notNull(pathItem.getDelete())){
                addFile(pathItem.getDelete(), rootNode, rootItem,MethodType.fromString("delete"),path);
            }
            if (notNull(pathItem.getOptions())){
                addFile(pathItem.getOptions(), rootNode, rootItem,MethodType.fromString("options"),path);
            }
            if (notNull(pathItem.getHead())){
                addFile(pathItem.getHead(), rootNode, rootItem,MethodType.fromString("head"),path);
            }
            if (notNull(pathItem.getPatch())){
                addFile(pathItem.getPatch(), rootNode, rootItem,MethodType.fromString("patch"),path);
            }
            if (notNull(pathItem.getTrace())){
                addFile(pathItem.getTrace(), rootNode, rootItem,MethodType.fromString("trace"),path);
            }
        }
        model.setRoot(rootItem);
    }

    private void addFile(Operation operation, TreeItemVO rootNode, DefaultMutableTreeNode rootItem, MethodType method,String path) {
        String tag = operation.getTags().isEmpty() ? "root" : operation.getTags().get(0);
        Optional<TreeItemVO> item = rootNode.getChildren().stream().filter(v->v.getTitle().equals(tag)).findFirst();
        if (item.isPresent()) {
            TreeItemVO node = new TreeItemVO();
            node.setMethod(method);
            node.setTitle(operation.getSummary());
            node.setUrl(path);
            node.setDirectory(false);
            node.setNode(operation);
            item.get().setUrl(toLowerCaseCamelCase(path));
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
                treeNode.setUrl(toLowerCaseCamelCase(path));
                TreeItemVO node = new TreeItemVO();
                node.setMethod(method);
                node.setTitle(operation.getSummary());
                node.setUrl(path);
                node.setDirectory(false);
                node.setNode(operation);
                DefaultMutableTreeNode fileItem = new DefaultMutableTreeNode(node);
                treeNode.getTreeNode().add(fileItem);
                node.setTreeNode(fileItem);
                treeNode.getChildren().add(node);
            }else{
                TreeItemVO node = new TreeItemVO();
                node.setMethod(method);
                node.setTitle(operation.getSummary());
                node.setUrl(path);
                node.setDirectory(false);
                node.setNode(operation);
                rootNode.setUrl(toLowerCaseCamelCase(path));
                DefaultMutableTreeNode fileItem = new DefaultMutableTreeNode(node);
                node.setTreeNode(fileItem);
                rootItem.add(fileItem);
            }
        }
    }

    private static String toLowerCaseCamelCase(String path) {
//        String[] paths = path.split("/");
//        String[] newArr = Arrays.copyOf(paths, paths.length - 1);
//        if (newArr.length == 0) {
//            return "";
//        }
        return  path.substring(0, path.lastIndexOf("/"));

//        StringBuilder sb = new StringBuilder();
//        boolean isFirstElement = true;
//        for (String s : newArr) {
//        if(s.isEmpty()){
//             continue;
//        }
//         if (!isFirstElement) {
//                // 首字母大写
//                s = s.substring(0, 1).toUpperCase() + s.substring(1);
//          }
//            sb.append(s);
//            isFirstElement = false;
//        }
//        return sb.toString();
    }
}
