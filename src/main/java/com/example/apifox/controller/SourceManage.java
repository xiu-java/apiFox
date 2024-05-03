package com.example.apifox.controller;

import com.example.apifox.component.ApiService;
import com.example.apifox.model.FileItem;
import com.example.apifox.model.Tree;
import com.example.apifox.view.FileTreeCell;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目: idea-plugins
 * <p>
 * 功能描述:
 * - 接口方法 ToolWindowFactory#createToolWindowContent 是需要自己工具框类实现的方法，
 * - 在这个 createToolWindowContent 方法中把自己的窗体 ReadUI 实例化后填充进去即可。
 * - 添加窗体的补助主要依赖于 ContentFactory.SERVICE.getInstance() 创建出 ContentFactory 并最终使用 toolWindow 添加窗体显示 UI 即可。
 * - 这里我们额外的还添加了一个全局属性 Config.readUI 这是为了后续可以在配置窗体中使用这个 UI 进行设置文件内容。
 *
 * @author: WuChengXing
 * @create: 2022-02-24 13:31
 **/
public class SourceManage implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建 JavaFX 面板
        JFXPanel fxPanel = new JFXPanel();
        VBox root = new VBox();

        // 确保在JavaFX应用程序线程上执行
        Platform.runLater(() -> {
            // 在此处构建你的JavaFX UI
            Scene scene = new Scene(root, 400, 300); // 假设是你需要的大小
            TreeView<FileItem> treeView = new TreeView<>();
            treeView.setCellFactory(tv -> new FileTreeCell());
            treeView.setStyle("-fx-tree-disclosure-node: url('/icons/apifox.png');");
            TreeItem<FileItem> rootItem = new TreeItem<>(new FileItem("Root", true));
            TreeItem<FileItem> folder1 = new TreeItem<>(new FileItem("Folder1", true));
            TreeItem<FileItem> folder2 = new TreeItem<>(new FileItem("Folder2", true));
            TreeItem<FileItem> file1 = new TreeItem<>(new FileItem("File1.txt", false));
            TreeItem<FileItem> file2 = new TreeItem<>(new FileItem("File2.txt", false));
            List<TreeItem<FileItem>> items = new ArrayList<>();
            items.add(folder1);
            items.add(folder2);
            rootItem.getChildren().addAll(items);
            folder1.getChildren().add(file1);
            folder2.getChildren().add(file2);
            treeView.setRoot(rootItem);
            ApiService service = project.getService(ApiService.class);
            Tree data = service.makeApiRequest("4282402");
            root.getChildren().add(treeView);
            fxPanel.setScene(scene);
        });

        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.getInstance();
        // 创建内容
        Content content = contentFactory.createContent(fxPanel, "", false);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content);
        // 其它你需要实现的代码
    }
}
