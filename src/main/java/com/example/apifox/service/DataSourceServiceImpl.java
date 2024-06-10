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
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public final class DataSourceServiceImpl implements DataSourceService {
    private Tree list;

    private Map<Long, List<ProjectVO>> project;

    public static DataSourceService getInstance(Project project) {
        return project.getService(DataSourceService.class);
    }

    DataSourceServiceImpl() {
        ApiService apiService = ApiServiceImpl.getInstance(ProjectManager.getInstance().getDefaultProject());
        ResponseVO data = apiService.getProject();
        if (data.getSuccess()) {
            project = data.getData().stream().collect(Collectors.groupingBy(ProjectVO::getTeamId));
        }


    }


    @Override
    public void upDateProjectById(Long projectId) {
        ApiService apiService = ApiServiceImpl.getInstance(ProjectManager.getInstance().getDefaultProject());
        CompletableFuture<Tree> future = apiService.projectDetail(String.valueOf(projectId));
        future.thenAccept(tree -> {
            // 在异步操作完成后处理结果
            if (tree != null) {
                DataUpdateTopic publisher = ProjectManager.getInstance().getDefaultProject().getMessageBus().syncPublisher(DataUpdateTopic.DATA_UPDATE_TOPIC);
                this.list = tree;
                publisher.dataUpdated(tree);
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
    public  Tree getDataSource() {
        return this.list;
    }
}
