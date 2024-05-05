package com.example.apifox.view;

import com.example.apifox.component.DataSourceService;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.TreeNode;
import com.intellij.openapi.project.ProjectManager;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SourcePane extends TreeView<TreeItemVO> {

    public SourcePane() {
        setHeight(800);
        configUi();
    }

    private void configUi() {
        getStyleClass().add("custom-tree-view");
        setFocusTraversable(false);
        setCellFactory(tv -> new CustomTreeCell());
        DataSourceService service = ProjectManager.getInstance().getDefaultProject().getService(DataSourceService.class);
        Map<String, List<TreeNode>> list = service.getDataSource();
        TreeItemVO rootNode = new TreeItemVO();
        rootNode.setRoot(true);
        rootNode.setDirectory(true);
        rootNode.setTitle("Root");
        rootNode.setChildren(new ArrayList<>());
        TreeItem<TreeItemVO> rootItem = new TreeItem<>(rootNode);
        for (Map.Entry<String, List<TreeNode>> entry : list.entrySet()) {
            String folder = entry.getKey();
            List<TreeNode> data = entry.getValue();
            TreeItemVO folderNodeVO = new TreeItemVO();
            folderNodeVO.setTitle(folder);
            folderNodeVO.setDirectory(true);
            folderNodeVO.setChildren(new ArrayList<>());
            rootNode.getChildren().add(folderNodeVO);
            TreeItem<TreeItemVO> folderItem = new TreeItem<>(folderNodeVO);
            rootItem.getChildren().add(folderItem);
            for (TreeNode treeNode : data) {
                TreeItemVO fileNodeVO = new TreeItemVO();
                fileNodeVO.setTitle(treeNode.getSummary());
                fileNodeVO.setNode(treeNode);
                folderNodeVO.getChildren().add(fileNodeVO);
                TreeItem<TreeItemVO> fileItem = new TreeItem<>(fileNodeVO);
                folderItem.getChildren().add(fileItem);
            }
        }
        setRoot(rootItem);
    }
}
