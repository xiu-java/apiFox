package com.example.apifox.component;


import com.example.apifox.model.ProjectVO;
import com.example.apifox.model.TreeNode;

import java.util.List;
import java.util.Map;

public interface DataSourceService {
    void upDateProjectById(Long projectId);

    public Map<Long,List<ProjectVO>> getProject();

    public List<TreeNode> getDataSource();
}
