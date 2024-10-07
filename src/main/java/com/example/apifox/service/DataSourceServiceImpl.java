package com.example.apifox.service;

import com.example.apifox.component.ApiService;
import com.example.apifox.component.DataSourceService;
import com.example.apifox.component.DataUpdateTopic;
import com.example.apifox.model.*;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.OpenAPI;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public final class DataSourceServiceImpl implements DataSourceService {
    private Openapi list;
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
        CompletableFuture<OpenAPI> future = apiService.projectDetail(String.valueOf(projectId));
        future.thenAccept(tree -> {
            // 在异步操作完成后处理结果
        });
        // 等待异步操作完成
        future.join();
    }

    @Override
    public Map<Long, List<ProjectVO>> getProject() {
        return this.project;
    }

    @Override
    public Openapi getDataSource() {
        return this.list;
    }
}
