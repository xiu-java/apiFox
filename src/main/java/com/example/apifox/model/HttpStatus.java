package com.example.apifox.model;

public enum HttpStatus {
        SUCCESS("200");
        private final String value;
        HttpStatus(String status) {
                this.value = status;
        }
        public String getValue() {
                return value;
        }
}
