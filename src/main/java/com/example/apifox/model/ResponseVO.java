package com.example.apifox.model;


import java.util.List;

public class ResponseVO {
    private List<ProjectVO> data;
    private boolean success;

    public List<ProjectVO> getData() { return data; }
    public void setData(List<ProjectVO> value) { this.data = value; }

    public boolean getSuccess() { return success; }
    public void setSuccess(boolean value) { this.success = value; }
}
