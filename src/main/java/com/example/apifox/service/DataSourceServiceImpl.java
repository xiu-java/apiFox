package com.example.apifox.service;

import com.example.apifox.component.ApiService;
import com.example.apifox.component.DataSourceService;
import com.example.apifox.model.Openapi;
import com.example.apifox.model.ProjectVO;
import com.example.apifox.model.ResponseVO;
import com.example.apifox.model.openapi.v3.models.Components;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public final class DataSourceServiceImpl implements DataSourceService {
    private Map<Long, List<ProjectVO>> project;
    Components components = null;


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
    public Components getComponents() {
        return components;
    }

    @Override
    public void setComponents(Components components) {
        this.components = components;
    }

    @Override
    public Map<Long, List<ProjectVO>> getProject() {
        return this.project;
    }

}
