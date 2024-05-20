package com.example.apifox.view;

import com.example.apifox.component.DataSourceService;
import com.example.apifox.interfaces.ComponentDelegate;
import com.example.apifox.model.ProjectVO;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        configUi();
    }


    public void configUi() {
        DataSourceService service = ProjectManager.getInstance().getDefaultProject().getService(DataSourceService.class);
        Map<Long, List<ProjectVO>> list = service.getProject();
        list.forEach((projectKey, items) -> {
            JPanel groupPanel = new JPanel() {
                {
                    setLayout(new BorderLayout(5, 5));
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
                   add(title, BorderLayout.NORTH);
                   JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
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

                   add(content, BorderLayout.CENTER);
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

}
