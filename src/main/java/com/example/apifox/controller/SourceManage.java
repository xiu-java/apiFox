package com.example.apifox.controller;

import com.example.apifox.view.Breadcrumb;
import com.example.apifox.view.ProjectPanel;
import com.example.apifox.view.SourcePanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowContentUiType;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
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
        JScrollPane projectPanel = new JScrollPane(new ProjectPanel());
        //创建JTree对象
        JScrollPane sourcePane =new JScrollPane(new SourcePanel());

        // 获取内容工厂的实例
        ContentFactory contentFactory = ContentFactory.getInstance();
        // 创建内容
        Content home = contentFactory.createContent(projectPanel, "Home", false);
        Content apis = contentFactory.createContent(sourcePane, "Apis", false);
        //定义分组组件
        JLabel label1 = new JLabel("账号名");
        label1.setFont(new Font("黑体", Font.PLAIN,20));
        JLabel label2 = new JLabel("密码");
        label2.setFont(new Font("黑体", Font.PLAIN,20));
        JPasswordField tf2 = new JPasswordField();
        tf2.setPreferredSize(new Dimension(0,25));
        JTextField tf1 = new JTextField();
        tf1.setPreferredSize(new Dimension(0,25));
        //分组布局的容器
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        //是否自动添加组件之间的间隙
        layout.setAutoCreateGaps(true);
        //是否自动在接触容器边缘的组件和容器之间创建间隙。
        layout.setAutoCreateContainerGaps(true);
        //为水平轴创建顺序组。
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        //顺序组依次包含两个平行组。
        hGroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label2));
        hGroup.addGroup(layout.createParallelGroup().addComponent(tf1).addComponent(tf2));
        layout.setHorizontalGroup(hGroup);
        //为垂直轴创建一个顺序组。
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        //顺序组包含两个对齐的并行组
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label1).addComponent(tf1));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label2).addComponent(tf2));
        layout.setVerticalGroup(vGroup);
        Content c2 = contentFactory.createContent(panel, "Home", false);
//        toolWindow.setDefaultContentUiType(ToolWindowContentUiType.TABBED);
        // 设置 ToolWindow 显示的内容
        toolWindow.getContentManager().addContent(home);
        toolWindow.getContentManager().addContent(apis);
        toolWindow.getContentManager().addContent(c2);

        // Initialize your JavaFX application here
        // 确保在JavaFX应用程序线程上执行
    }

    private JComponent createMyToolWindowContent() {
        // 创建和返回工具窗口的主要内容
        return new JLabel("This is my ToolWindow content");
    }

    private JComponent createBreadcrumbsComponent() {
        // 创建你自己的面包屑组件，这只是示例
        return new JLabel("Breadcrumb1 > Breadcrumb2 > Breadcrumb3");
    }
}
