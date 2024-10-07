package com.example.apifox.component;

import com.example.apifox.model.ResponseVO;
import com.example.apifox.model.Openapi;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.OpenAPI;

import java.util.concurrent.CompletableFuture;

public interface ApiService {

    Components components = null;

    CompletableFuture<OpenAPI> projectDetail(String projectId);

    ResponseVO getProject();
}
