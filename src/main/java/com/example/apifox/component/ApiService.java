package com.example.apifox.component;

import com.example.apifox.model.ResponseVO;
import com.example.apifox.model.Tree;

import java.util.concurrent.CompletableFuture;

public interface ApiService {


    CompletableFuture<Tree> projectDetail(String projectId);

    ResponseVO getProject();
}
