package com.example.apifox.model;

import java.util.List;
import java.util.Map;

public class Example {
    private String fieldSting; // 假设字段名为 "fieldName"
    private Map<String,String> fieldObject; // 假设字段值可以是 MyClassObject 类型
    private List<Map<String,String>> fieldList;
    public Example() {

    }
    // 构造函数
    public Example(String fieldName) {
        this.fieldSting = fieldName;
    }

    public Example(Map<String,String> fieldValue) {
        this.fieldObject = fieldValue;
    }

    public String getFieldSting() {
        return fieldSting;
    }

    public void setFieldSting(String fieldSting) {
        this.fieldSting = fieldSting;
    }

    public Map<String, String> getFieldObject() {
        return fieldObject;
    }

    public void setFieldObject(Map<String, String> fieldObject) {
        this.fieldObject = fieldObject;
    }

    public List<Map<String, String>> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Map<String, String>> fieldList) {
        this.fieldList = fieldList;
    }
}
