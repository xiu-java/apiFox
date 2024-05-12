package com.example.apifox.view;

import com.example.apifox.component.DataSourceService;
import com.example.apifox.model.ProjectVO;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ProjectPanel extends JPanel {

    public ProjectPanel() {
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
                   add(title, BorderLayout.NORTH);
                   JPanel content = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
                    SwingUtilities.invokeLater(()->{
                        items.forEach((item)->{
                            JPanel projectView = new JPanel();
                            projectView.setBackground(Color.blue);
                            projectView.setLayout(new BoxLayout(projectView, BoxLayout.PAGE_AXIS));
                            RoundImageIcon icon = new RoundImageIcon(item.getIcon(),20,50,50);
                            icon.setPreferredSize(new Dimension(80,50));
                            icon.setMaximumSize(new Dimension(80,50));
                            icon.setBackground(Color.red);
                            icon.setHorizontalAlignment(SwingConstants.CENTER);
                            JLabel name = new JLabel(item.getName());
                            name.setPreferredSize(new Dimension(80,20));
                            name.setMaximumSize(new Dimension(80,20));
                            name.setHorizontalAlignment(SwingConstants.CENTER);
//                            projectView.add(Box.createVerticalGlue());
                            projectView.add(Box.createVerticalStrut(10));
                            projectView.add(icon);
                            projectView.add(Box.createVerticalStrut(10));
                            projectView.add(name);
                            content.add(projectView);
                        });
                    });

                   add(content, BorderLayout.CENTER);
                }
            };

            add(groupPanel);
        });


    }

}
