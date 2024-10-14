package com.example.apifox.view;

import com.example.apifox.component.DataSourceService;
import com.example.apifox.interfaces.ComponentDelegate;
import com.example.apifox.layout.ContentLayout;
import com.example.apifox.layout.ProjectGroupLayout;
import com.example.apifox.model.ProjectVO;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class ProjectPanel extends JPanel {
    private static ComponentDelegate<Long> delegate;

    public ProjectPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        configUi();
    }

    public ProjectPanel(ComponentDelegate<Long> componentDelegate) {
        delegate = componentDelegate;
        setLayout(new CustomFlowLayout());
        configUi();
    }


    public void configUi() {
        DataSourceService service = ProjectManager.getInstance().getDefaultProject().getService(DataSourceService.class);
        Map<Long, List<ProjectVO>> list = service.getProject();
        list.forEach((projectKey, items) -> {
            JPanel groupPanel = new JPanel() {
                {
                    setLayout(new ProjectGroupLayout());
                    configUi();
                }

                public void configUi() {
                   JLabel title = new JLabel("groupId:"+projectKey);
                   title.setFont(title.getFont().deriveFont(Font.BOLD));
                   title.setOpaque(true);
                   title.setBackground(JBColor.background().darker());
                   title.setForeground(JBColor.foreground().darker());
                   title.setPreferredSize(new Dimension(title.getWidth(),30));
                   title.setBorder(JBUI.Borders.emptyLeft(10));
                   add(title);
                   JPanel content = new JPanel(new ContentLayout());
                   SwingUtilities.invokeLater(()->{
                        items.forEach((item)->{
                            JPanel projectView = new JPanel(new GridBagLayout());
//                            projectView.setBackground(Color.blue);
                            GridBagConstraints gbc = new GridBagConstraints();
                            gbc.gridwidth = GridBagConstraints.REMAINDER;
                            gbc.anchor = GridBagConstraints.CENTER;
                            gbc.insets = JBUI.insetsBottom(5);
                            RoundImageIcon icon = getIcon(item);
                            JLabel name = new JLabel(item.getName());
                            name.setPreferredSize(new Dimension(80,20));
                            JButton description = new JButton();
                            description.setPreferredSize(new Dimension(50,50));
                            description.setMaximumSize(new Dimension(50,50));
                            description.setBackground(Color.BLUE);
                            name.setHorizontalAlignment(SwingConstants.CENTER);
//                            projectView.add(Box.createVerticalGlue());
//                            projectView.add(Box.createVerticalStrut(10));
//                            projectView.add(Box.createHorizontalGlue());
                            projectView.add(icon,gbc);
//                            projectView.add(Box.createVerticalGlue());
                            projectView.add(name,gbc);
                            projectView.add(Box.createHorizontalGlue());
                            content.add(projectView);
                        });
                    });
                   add(content);
                }

                private static @NotNull RoundImageIcon getIcon(ProjectVO item) {
                    RoundImageIcon icon = new RoundImageIcon(item.getIcon(),20,50,50);
//                            projectView.setAlignmentX(SwingConstants.CENTER);
                    icon.setPreferredSize(new Dimension(50,50));
//                            icon.setMaximumSize(new Dimension(50,50));
                    icon.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            delegate.onButtonClicked(item.getId());
                        }
                    });
                    return icon;
                }
            };
            add(groupPanel);
        });


    }
    // 自定义FlowLayout类
    public static class CustomFlowLayout extends FlowLayout {
        public CustomFlowLayout() {
            super(FlowLayout.LEFT, 5, 5);
        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                int ncomponents = parent.getComponentCount();
                int maxWidth = parent.getWidth() - getHgap() * 2;
                int maxHeight = 0;

                for (int i = 0; i < ncomponents; i++) {
                    Component comp = parent.getComponent(i);
                    if (comp.isVisible()) {
                        Dimension d = comp.getPreferredSize();
                        comp.setSize(maxWidth, d.height); // 设置宽度为父容器的宽度
                        maxHeight += d.height + getVgap();
                    }
                }

                return new Dimension(maxWidth, maxHeight - getVgap());
            }
        }

        @Override
        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                int ncomponents = parent.getComponentCount();
                int maxWidth = parent.getWidth() - getHgap() * 2;
                int maxHeight = 0;
                int x = getHgap();
                int y = getVgap();

                for (int i = 0; i < ncomponents; i++) {
                    Component comp = parent.getComponent(i);
                    if (comp.isVisible()) {
                        Dimension d = comp.getPreferredSize();
                        comp.setSize(maxWidth, d.height); // 设置宽度为父容器的宽度
                        comp.setBounds(x, y, maxWidth, d.height);
                        y += d.height + getVgap();
                    }
                }
            }
        }
    }

}
