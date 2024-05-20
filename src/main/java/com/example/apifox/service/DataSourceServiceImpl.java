package com.example.apifox.service;

import com.example.apifox.component.ApiService;
import com.example.apifox.component.DataSourceService;
import com.example.apifox.component.DataUpdateTopic;
import com.example.apifox.model.*;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.messages.MessageBus;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public final class DataSourceServiceImpl implements DataSourceService {
    private final MessageBus messageBus;
    private List<TreeNode> list = new ArrayList<>();

    private Map<Long, List<ProjectVO>> project;

    public static DataSourceService getInstance(Project project) {
        return project.getService(DataSourceService.class);
    }

    DataSourceServiceImpl() {
        messageBus = ProjectManager.getInstance().getDefaultProject().getMessageBus();
        ApiService apiService = ApiServiceImpl.getInstance(ProjectManager.getInstance().getDefaultProject());
        ResponseVO data = apiService.getProject();
        if (data.getSuccess()) {
            project = data.getData().stream().collect(Collectors.groupingBy(ProjectVO::getTeamId));
        }


    }


    private void analyzer(Tree data) {
        Map<String,TreeNode> tree = data.getTags().stream().map(str -> {
            TreeNode node = new TreeNode();
            node.title = str.getName();
            node.isFolder = true;
            node.children = new ArrayList<>();
            return node;
        }).collect(Collectors.toMap(TreeNode::getTitle,p->p));
        for (Map.Entry<String, Item> entry : data.getPaths().entrySet()) {
            String url = entry.getKey();
            Item item = entry.getValue();
            TreeNode node = new TreeNode();
            node.setFolder(false);
            node.setTitle(url);
            if (item.getGet() != null) {
                node.setMethod(MethodType.GET);
                Detail detail = item.getGet();
                copyNode(detail, tree, node);
            }
            if (item.getPost() != null) {
                node.setMethod(MethodType.POST);
                Detail detail = item.getPost();
                copyNode(detail, tree, node);

            }
            if (item.getPut() != null) {
                node.setMethod(MethodType.PUT);
                Detail detail = item.getPut();
                copyNode(detail, tree, node);
            }
            if (item.getDelete() != null) {
                node.setMethod(MethodType.DELETE);
                Detail detail = item.getDelete();
                copyNode(detail, tree, node);
            }
        }
        this.list = new ArrayList<>(tree.values());
        DataUpdateTopic publisher = messageBus.syncPublisher(DataUpdateTopic.DATA_UPDATE_TOPIC);
        publisher.dataUpdated(this.list);
    }

    private static void copyNode(Detail detail, Map<String, TreeNode> tree, TreeNode node) {
        String key = detail.getTags().isEmpty() ? "root" : detail.getTags().get(0);
        if (tree.containsKey(key)) {
            tree.get(key).children.add(node);
        } else {
            tree.put(node.getTitle(), node);
        }
        try {
            copyProperties(node, detail);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void upDateProjectById(Long projectId) {
        ApiService apiService = ApiServiceImpl.getInstance(ProjectManager.getInstance().getDefaultProject());
        CompletableFuture<Tree> future = apiService.projectDetail(String.valueOf(projectId));
        future.thenAccept(tree -> {
            // 在异步操作完成后处理结果
            if (tree != null) {
                this.analyzer(tree);
            } else {
                System.out.println("API request failed.");
            }
        });
        // 等待异步操作完成
        future.join();
    }

    @Override
    public Map<Long, List<ProjectVO>> getProject() {
        return this.project;
    }

    @Override
    public  List<TreeNode> getDataSource() {
        return this.list;
    }
}
