package com.example.apifox.controller;

import com.example.apifox.component.DataSourceService;
import com.example.apifox.interfaces.ComponentDelegate;
import com.example.apifox.interfaces.DetailDelegate;
import com.example.apifox.model.Detail;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.Operation;
import com.example.apifox.view.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

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
public class SourceManage implements ToolWindowFactory, ComponentDelegate<Long>, DetailDelegate {
    private ToolWindow window;

    private final ProjectPanel projectPanel = new ProjectPanel(this);
    private final ApiPanel apiPanel = new ApiPanel(this);
    private final DetailPanel detailPanel = new DetailPanel();

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        this.window = toolWindow;
        //创建JTree对象
        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.getInstance();
        // 创建内容
        Content home = contentFactory.createContent(new JBScrollPane(projectPanel), "Home", false);
        Content apis = contentFactory.createContent(apiPanel, "Apis", false);
        Content detail = contentFactory.createContent(detailPanel, "Detail", false);
        toolWindow.getContentManager().addContent(home);
        toolWindow.getContentManager().addContent(apis);
        toolWindow.getContentManager().addContent(detail);
    }

    @Override
    public void onButtonClicked(Long projectId) {
        Content currentContent = window.getContentManager().findContent("Apis");
        window.getContentManager().setSelectedContent(currentContent);
        // 后续代码放在一个新的线程中执行
        new Thread(() -> {
            // 这里是后续的代码
            apiPanel.open(projectId);
        }).start();

    }

    @Override
    public void onDetailClick(TreeItemVO detail) {
        Content currentContent = window.getContentManager().findContent("Detail");
        window.getContentManager().setSelectedContent(currentContent);
        detailPanel.open(detail);
    }
}
