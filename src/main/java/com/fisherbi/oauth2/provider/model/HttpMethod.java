package com.fisherbi.oauth2.provider.model;

/**
 * Created by FisherBi on 2019/1/28.
 */
public enum  HttpMethod {

    GET(false), POST(true), PUT(true), DELETE(false, true), HEAD(false), OPTIONS(false), TRACE(false), PATCH(true);

    private final boolean requiresBody;
    private final boolean permitBody;

    HttpMethod(boolean requiresBody) {
        this(requiresBody, requiresBody);
    }

    HttpMethod(boolean requiresBody, boolean permitBody) {
        if (requiresBody && !permitBody) {
            throw new IllegalArgumentException();
        }
        this.requiresBody = requiresBody;
        this.permitBody = permitBody;
    }

    public boolean isRequiresBody() {
        return requiresBody;
    }

    public boolean isPermitBody() {
        return permitBody;
    }
}
