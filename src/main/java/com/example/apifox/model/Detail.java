package com.example.apifox.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Detail {
    private String url;
    private String summary;
    private boolean deprecated;
    private String description;
    private MethodType method;
    private List<String> tags;
    private List<Parameters> parameters;
    private RequestBody requestBody;
    private Map<String, Responses> responses =  new HashMap<>();
    private List<String> security;
    @SerializedName("x-apifox-folder")
    private String xApifoxFolder;
    @SerializedName("x-apifox-status")
    private String xApifoxStatus;
    @SerializedName("x-run-in-apifox")
    private String xRunInApifox;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MethodType getMethod() {
        return method;
    }

    public void setMethod(MethodType method) {
        this.method = method;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Parameters> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameters> parameters) {
        this.parameters = parameters;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, Responses> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, Responses> responses) {
        this.responses = responses;
    }

    public List<String> getSecurity() {
        return security;
    }

    public void setSecurity(List<String> security) {
        this.security = security;
    }

    public String getxApifoxFolder() {
        return xApifoxFolder;
    }

    public void setxApifoxFolder(String xApifoxFolder) {
        this.xApifoxFolder = xApifoxFolder;
    }

    public String getxApifoxStatus() {
        return xApifoxStatus;
    }

    public void setxApifoxStatus(String xApifoxStatus) {
        this.xApifoxStatus = xApifoxStatus;
    }

    public String getxRunInApifox() {
        return xRunInApifox;
    }

    public void setxRunInApifox(String xRunInApifox) {
        this.xRunInApifox = xRunInApifox;
    }
}
