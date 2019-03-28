package com.fisherbi.client.oauth2.model;


import com.fisherbi.client.oauth2.utils.Preconditions;

import java.util.Objects;


public class OAuth2AccessToken extends Token {

    private static final long serialVersionUID = 8901381135476613449L;

    private String accessToken;

    private String tokenType;

    private Integer expiresIn;

    private String refreshToken;

    private String scope;

    public OAuth2AccessToken(String accessToken) {
        this(accessToken, null);
    }

    public OAuth2AccessToken(String accessToken, String rawResponse) {
        this(accessToken, null, null, null, null, rawResponse);
    }

    public OAuth2AccessToken(String accessToken, String tokenType, Integer expiresIn, String refreshToken, String scope,
                             String rawResponse) {
        super(rawResponse);
        Preconditions.checkNotNull(accessToken, "access_token can't be null");
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
        this.scope = scope;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(accessToken);
        hash = 41 * hash + Objects.hashCode(tokenType);
        hash = 41 * hash + Objects.hashCode(expiresIn);
        hash = 41 * hash + Objects.hashCode(refreshToken);
        hash = 41 * hash + Objects.hashCode(scope);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OAuth2AccessToken other = (OAuth2AccessToken) obj;
        if (!Objects.equals(accessToken, other.getAccessToken())) {
            return false;
        }
        if (!Objects.equals(tokenType, other.getTokenType())) {
            return false;
        }
        if (!Objects.equals(refreshToken, other.getRefreshToken())) {
            return false;
        }
        if (!Objects.equals(scope, other.getScope())) {
            return false;
        }
        return Objects.equals(expiresIn, other.getExpiresIn());
    }
}
