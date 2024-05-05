package com.example.apifox.controller;

import com.example.apifox.component.DataSourceService;
import com.example.apifox.model.*;
import com.example.apifox.view.CustomTreeCell;
import com.example.apifox.view.SchemaPane;
import com.example.apifox.view.SourcePane;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.*;

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
    private  final JFXPanel fxPanel = new JFXPanel();
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建 JavaFX 面板
        SwingUtilities.invokeLater(() -> {
            Platform.runLater(() -> {
                StackPane stackPane = new StackPane();
                stackPane.setStyle("-fx-background-color: lightblue;");
                SourcePane sourcePane = new SourcePane();
                SchemaPane schemaPane = new SchemaPane();
                stackPane.getChildren().addAll(schemaPane,sourcePane);
                stackPane.setMinSize(StackPane.USE_PREF_SIZE, StackPane.USE_PREF_SIZE);
                sourcePane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                        // 创建淡出动画
                        TreeItemVO selectedValue = sourcePane.getSelectionModel().getSelectedItem().getValue();
                        System.out.println(selectedValue);
                        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), sourcePane);
                        fadeOut.setFromValue(1.0);
                        fadeOut.setToValue(0.0);
                        fadeOut.setOnFinished(animation -> {
                            // 隐藏TreeView
                            sourcePane.setVisible(false);
                            // 创建淡入动画
//                        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), schemaPane);
//                        fadeIn.setFromValue(0.0);
//                        fadeIn.setToValue(1.0);
//                        fadeIn.play();
                        });
                        fadeOut.play();
                    }
                });
                Scene scene = new Scene(stackPane, 400, 300); // 假设是你需要的大小
                scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
                fxPanel.setScene(scene);
            });

            // 获取内容工厂的实例
            ContentFactory contentFactory = ContentFactory.getInstance();
            // 创建内容
            Content content = contentFactory.createContent(fxPanel, "", false);
            // 设置 ToolWindow 显示的内容
            toolWindow.getContentManager().addContent(content);
            // Initialize your JavaFX application here
        });
        // 确保在JavaFX应用程序线程上执行
    }
}
