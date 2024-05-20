package com.example.apifox.model;

import java.util.Map;

public class Body {
    private Schema schema;
    private Example example;

    public Schema getSchema() {
        return schema;
    }

    public void setSchema(Schema schema) {
        this.schema = schema;
    }

    public Example getExample() {
        return example;
    }

    public void setExample(Example example) {
        this.example = example;
    }
}
