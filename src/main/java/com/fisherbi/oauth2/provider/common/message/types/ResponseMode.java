package com.fisherbi.oauth2.provider.common.message.types;

/**
 * Created by fisbii on 18-8-3.
 */
public enum ResponseMode {


    QUERY("query"),
    FRAGMENT("fragment");


    private String mode;

    ResponseMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return mode;
    }

}
