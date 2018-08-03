package com.fisherbi.oauth2.provider.common.message.types;

/**
 * Created by fisbii on 18-8-3.
 */
public enum TokenTypeHint {

    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private String hint;

    TokenTypeHint(String hint) {
        this.hint = hint;
    }

    @Override
    public String toString() {
        return hint;
    }
}
