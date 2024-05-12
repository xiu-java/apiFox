package com.example.apifox.service;

import com.example.apifox.component.ApiService;
import com.example.apifox.component.DataSourceService;
import com.example.apifox.model.*;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import static org.apache.commons.beanutils.BeanUtils.copyProperties;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    private final Map<String, List<TreeNode>> list = new HashMap<>();

    private Map<Long,List<ProjectVO>> project;

    public static DataSourceService getInstance(Project project) {
        return project.getService(DataSourceService.class);
    }

    DataSourceServiceImpl(){
        ApiService apiService = ApiServiceImpl.getInstance(ProjectManager.getInstance().getDefaultProject());
        ResponseVO data =  apiService.getProject();
        if(data.getSuccess()){
            project = data.getData().stream().collect(Collectors.groupingBy(ProjectVO::getTeamId));
        }
        Tree tree = apiService.makeApiRequest("4282402");
        this.analyzer(tree);
    }

    private void analyzer(Tree data){
            Map<String, List<TreeNode>> tree = new HashMap<>();
            for (Map.Entry<String, Item> entry : data.getPaths().entrySet()) {
                String url = entry.getKey();
                Item item = entry.getValue();
                Detail detail = null;
                TreeNode node =new TreeNode();
                node.setUrl(url);
                if (item.getGet()!= null) {
                    detail = item.getGet();
                    node.setMethod(MethodType.GET);
                }
                if (item.getPost()!= null) {
                    detail = item.getPost();
                    node.setMethod(MethodType.POST);
                }
                if (item.getPut()!= null) {
                    detail = item.getPut();
                    node.setMethod(MethodType.PUT);
                }
                if (item.getDelete()!= null) {
                    detail = item.getDelete();
                    node.setMethod(MethodType.DELETE);
                }
                try {
                    copyProperties(node, detail);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                assert detail != null;
                String key =detail.getTags().isEmpty() ? "root" : detail.getTags().get(0);
                if(this.list.containsKey(key)){
                    this.list.get(key).add(node);
                }else {
                    this.list.computeIfAbsent(key, k -> new ArrayList<>()).add(node);
                }
            }
    }

    @Override
    public Map<Long,List<ProjectVO>> getProject() {
        return this.project;
    }

    @Override
    public Map<String, List<TreeNode>> getDataSource() {
        return this.list;
    }
}
