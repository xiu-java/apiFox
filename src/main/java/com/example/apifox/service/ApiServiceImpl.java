package com.example.apifox.service;

import com.example.apifox.component.ApiService;
import com.example.apifox.model.Example;
import com.example.apifox.model.Item;
import com.example.apifox.model.ResponseVO;
import com.example.apifox.model.openapi.v3.models.Components;
import com.example.apifox.model.openapi.v3.models.OpenAPI;
import com.example.apifox.utils.ExampleDeserializer;
import com.example.apifox.utils.ItemDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import okhttp3.*;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public final class ApiServiceImpl implements ApiService {
    Components components = null;
    final OkHttpClient client = new OkHttpClient();
    final private Gson gson = new GsonBuilder()
            .registerTypeAdapter(Example.class, new ExampleDeserializer())
            .registerTypeAdapter(Item.class, new ItemDeserializer())
            .create();
    private final String token;
    MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public ApiServiceImpl() {
        this.token = PropertiesComponent.getInstance().getValue("ApiFox.Token");
    }

    public static ApiService getInstance(Project project) {
        return project.getService(ApiService.class);
    }

    @Override
    public CompletableFuture<OpenAPI> projectDetail(String projectId) {
        CompletableFuture<OpenAPI> future = new CompletableFuture<>();
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
                .addHeader("Authorization", "Bearer " + this.token)
                .addHeader("X-Apifox-Version", "2024-01-20")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // 处理请求失败的情况
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 处理请求成功的情况
                if (response.isSuccessful()) {
                    // 获取响应数据
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    OpenAPI data = gson.fromJson(responseBody, OpenAPI.class);
                    components = data.getComponents();
                    future.complete(data);
                } else {
                    // 处理请求失败的情况
                    future.completeExceptionally(new RuntimeException("Request failed with code: " + response.code()));
                }
            }
        });
        return  future;
    }

    // Service implementation
    @Override
   public ResponseVO getProject(){
        String url = "https://api.apifox.com/api/v1/user-projects";

        // 使用HttpUrl构建器构造带查询参数的URL
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse(url)).newBuilder()
                .addQueryParameter("locale", "zh-CN")
                .build();

        Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("Authorization","Bearer "+this.token)
                .addHeader("X-Apifox-Version","2024-01-20")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            assert response.body() != null;
            String responseBody = response.body().string();
            return gson.fromJson(responseBody, ResponseVO.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
