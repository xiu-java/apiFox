package com.example.apifox.service;

import com.example.apifox.component.DataSourceService;
import com.example.apifox.model.openapi.v3.models.Components;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;

@Service(Service.Level.PROJECT)
public final class ProjectConfigImpl implements  DataSourceService {

    public static ProjectConfigImpl getInstance(Project project) {
        return project.getService(ProjectConfigImpl.class);
    }


    public static class State {
        Components components = null;
    }

    public Components getComponents() {
        return state.components;
    }

    public void setComponents(Components components) {
        state.components = components;
    }


    private State state = new State();
}
