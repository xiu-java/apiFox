package com.example.apifox.service;

import com.example.apifox.model.openapi.v3.models.Components;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service(Service.Level.PROJECT)
@State(name = "MyPluginConfig", storages = @Storage("myplugin.xml"))
public final class ProjectConfig implements PersistentStateComponent<ProjectConfig.State> {

    public static ProjectConfig getInstance(Project project) {
        return project.getService(ProjectConfig.class);
    }

    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull ProjectConfig.State state) {
          this.state = state;
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
