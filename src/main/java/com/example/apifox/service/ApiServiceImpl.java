package com.example.apifox.service;

import com.example.apifox.component.ApiService;
import com.example.apifox.component.DataSourceService;
import com.example.apifox.model.Tree;
import com.google.gson.Gson;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ApiServiceImpl implements ApiService {
    final OkHttpClient client = new OkHttpClient();
    final private Gson gson = new Gson();
    MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static ApiService getInstance(Project project) {
        return project.getService(ApiService.class);
    }

    @Override
    public Tree makeApiRequest(String projectId) {
        String url = "https://api.apifox.com/api/v1/projects/" + projectId + "/export-openapi";

        // 使用HttpUrl构建器构造带查询参数的URL
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder()
                .addQueryParameter("locale", "zh-CN")
                .build();
        // 创建一个要发送的对象
        // 将对象转为JSON字符串
        String jsonString = "{\"version\": \"3.0\",\"openApiFormat\":\"yaml\"}"; // 替换为自己的JSON
        RequestBody body = RequestBody.create(jsonString, JSON);

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("Authorization","Bearer APS-p0rvMBQkKjf5vrdKi65D6OB2Gy0TN9i0")
                .addHeader("X-Apifox-Version","2024-01-20")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            assert response.body() != null;
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, Tree.class);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    // Service implementation

}
