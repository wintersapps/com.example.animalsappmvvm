package com.examples.animalsappmvvm.model;

public class ApiKeyModel {
    private String message;
    private String key;

    public ApiKeyModel(String message, String key) {
        this.message = message;
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
