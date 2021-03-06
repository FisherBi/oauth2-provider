package com.fisherbi.client.oauth2.model;

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
