package com.CouponSystemStage2.CouponSystem.AdviceController;

public class ErrorDetails {
    private String key;
    private String value;

    public ErrorDetails(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
