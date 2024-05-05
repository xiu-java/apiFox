package com.example.apifox.component;

import com.example.apifox.model.ResponseVO;
import com.example.apifox.model.Tree;

public interface ApiService {

    // Service implementation
    Tree makeApiRequest(String projectId);

    ResponseVO getProject();
}
