package com.example.apifox.model;

import java.util.Map;

public class Body {
    private Schema schema;
    private Map<String,String> example;

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Map<String, String> getExample() {
        return example;
    }

    public void setExample(Map<String, String> example) {
        this.example = example;
    }
}
