package com.fisherbi.client.oauth2.model;

import com.fisherbi.client.oauth2.exception.OAuthException;

import java.net.URI;

public class OAuth2AccessTokenErrorResponse extends OAuthException {

    private static final long serialVersionUID = 2309424849700276816L;

    public enum ErrorCode {
        invalid_request, invalid_client, invalid_grant, unauthorized_client, unsupported_grant_type, invalid_scope,
    }

    private final ErrorCode errorCode;
    private final String errorDescription;
    private final URI errorUri;
    private final String rawResponse;

    public OAuth2AccessTokenErrorResponse(ErrorCode errorCode, String errorDescription, URI errorUri,
                                          String rawResponse) {
        super(rawResponse);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
        this.errorUri = errorUri;
        this.rawResponse = rawResponse;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public URI getErrorUri() {
        return errorUri;
    }

    public String getRawResponse() {
        return rawResponse;
    }
}
