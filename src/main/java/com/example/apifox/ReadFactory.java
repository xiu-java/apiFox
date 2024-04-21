package com.example.apifox;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

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
public class ReadFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建 JavaFX 面板
        JFXPanel fxPanel = new JFXPanel();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("read-factory.fxml"));
// 创建一个垂直布局
        VBox root = new VBox();
        root.setAlignment(javafx.geometry.Pos.CENTER);
        root.setSpacing(20.0);

        // 设置布局的内边距
        Insets insets = new Insets(20.0);
        root.setPadding(insets);

        // 创建一个按钮
        Button button = new Button();
        button.setText("Hello!");

        // 添加按钮点击事件处理程序
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello Button Clicked!");
            }
        });

        // 将按钮添加到垂直布局中
        root.getChildren().add(button);

        // 创建 JavaFX 场景，并设置根节点
        Scene scene = new Scene(root, 400, 300);;


        fxPanel.setScene(scene);
        // 将 JavaFX UI 添加到场景中
        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.getInstance();
        // 创建内容
        Content content = contentFactory.createContent(fxPanel, "", false);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(content);
        // 全局使用
    }
}
