package com.example.apifox.model;

public enum MethodType {
        GET("get"), POST("post"), PUT("put"), DELETE("delete"), OPTIONS("options"), HEAD("head"), PATCH("patch"), TRACE("trace");
        private final String value;
        MethodType(String status) {
                this.value = status;
        }
        public String getValue() {
                return value;
        }
        public static MethodType fromString(String text) {
                if (text != null) {
                        for (MethodType methodType : MethodType.values()) {
                                if (text.equalsIgnoreCase(methodType.value)) {
                                        return methodType;
                                }
                        }
                }
                return null;
        }
}
