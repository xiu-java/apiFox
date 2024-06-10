package com.example.apifox.view;

import com.example.apifox.component.ApiService;
import com.example.apifox.interfaces.DetailDelegate;
import com.example.apifox.model.Tree;
import com.example.apifox.model.TreeItemVO;
import com.example.apifox.service.ApiServiceImpl;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.ui.JBColor;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.CompletableFuture;

public class ApiPanel extends JPanel {
    CardLayout cardLayout = new CardLayout();
    private final SourcePanel sourcePane =new SourcePanel();
    private final LoadingPanel loadingPanel =new LoadingPanel("加载中...",15,0);
    public ApiPanel(DetailDelegate detailDelegate) {
        sourcePane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // 获取点击位置的路径
                    TreePath selPath = sourcePane.getPathForLocation(e.getX(), e.getY());
                    if (selPath != null) {
                        // 获取最后一个组件，即被双击的节点
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selPath.getLastPathComponent();
                        // 获取节点的数据
                        Object nodeData = selectedNode.getUserObject();
                        if(nodeData instanceof TreeItemVO treeItemVO) {
                            detailDelegate.onDetailClick(treeItemVO.getNode());
                        }
                    }
                }
            }
        });
        setLayout(cardLayout);
        this.configUi();
    }

    private void configUi() {
        add("loading", new JLabel(){
            {
                setBackground(JBColor.green);
                loadingPanel.setBounds(150,300,200,200);
                add(loadingPanel);

            }
        });
        add("content",new JScrollPane(sourcePane));
    }

    public void open(Long projectId){
        loadingPanel.start();
        cardLayout.show(this, "loading");
        ApiService apiService = ApiServiceImpl.getInstance(ProjectManager.getInstance().getDefaultProject());
        CompletableFuture<Tree> future = apiService.projectDetail(String.valueOf(projectId));
        future.thenAccept(tree -> {
            // 在异步操作完成后处理结果
            if (tree != null) {
                this.sourcePane.updateUi(tree);
            } else {
                System.out.println("API request failed.");
            }
            loadingPanel.stop();
            cardLayout.show(this, "content");
        });
        // 等待异步操作完成
        future.join();
    }
}
