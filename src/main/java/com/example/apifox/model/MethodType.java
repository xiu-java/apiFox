package com.example.apifox.model;

public enum MethodType {
        GET("get"), POST("post"), PUT("put"), DELETE("delete");
        private final String value;
        MethodType(String status) {
                this.value = status;
        }
        public String getValue() {
                return value;
        }

}
