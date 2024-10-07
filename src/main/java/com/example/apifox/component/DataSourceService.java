package com.example.apifox.component;


import com.example.apifox.model.ProjectVO;
import com.example.apifox.model.Openapi;
import com.example.apifox.model.openapi.v3.models.Components;

import java.util.List;
import java.util.Map;

public interface DataSourceService {

    void upDateProjectById(Long projectId);

    public Map<Long,List<ProjectVO>> getProject();

    public Openapi getDataSource();
}
