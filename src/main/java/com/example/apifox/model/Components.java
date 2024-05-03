package com.example.apifox.model;

import com.google.protobuf.Any;

import java.util.Map;

public class Components {
    private Map<String,Schema> schemas;
    private Map<String, Any> securitySchemes;
}
