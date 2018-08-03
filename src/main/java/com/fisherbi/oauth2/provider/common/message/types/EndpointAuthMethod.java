package com.fisherbi.oauth2.provider.common.message.types;

/**
 * Created by fisbii on 18-8-3.
 */
public enum EndpointAuthMethod {

    NONE("none"),
    CLIENT_SECRET_BASIC("client_secret_basic"),
    CLIENT_TOKEN_BEARER("client_token_bearer"),
    CLIENT_SECRET_POST("client_secret_post"),
    USER_TOKEN_BEARER("user_token_bearer"),
    PRIVATE_KEY_JWT("private_key_jwt");


    private String method;

    EndpointAuthMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return method;
    }
}
