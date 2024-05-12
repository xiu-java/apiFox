package com.example.apifox.component;


import com.example.apifox.model.ProjectVO;
import com.example.apifox.model.TreeNode;

import java.util.List;
import java.util.Map;

public interface DataSourceService {
   public Map<Long,List<ProjectVO>> getProject();

    public Map<String, List<TreeNode>> getDataSource();
}
