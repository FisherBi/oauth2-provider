package com.fisherbi.client.oauth2.service;

import com.fisherbi.client.oauth2.api.DefaultApi20;
import com.fisherbi.client.oauth2.extractor.OAuth2AccessTokenJsonExtractor;
import com.fisherbi.client.oauth2.httpclient.HttpClient;
import com.fisherbi.client.oauth2.httpclient.HttpClientConfig;
import com.fisherbi.client.oauth2.model.*;
import com.fisherbi.client.oauth2.pkce.AuthorizationUrlWithPKCE;
import com.fisherbi.client.oauth2.pkce.PKCE;
import com.fisherbi.client.oauth2.pkce.PKCEService;
import com.fisherbi.client.oauth2.revoke.TokenTypeHint;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class OAuth20Service extends OAuthService {

    private static final String VERSION = "2.0";
    private static final PKCEService PKCE_SERVICE = new PKCEService();
    private final DefaultApi20 api;
    private final String responseType;
    private final String state;

    public OAuth20Service(DefaultApi20 api, String apiKey, String apiSecret, String callback, String scope,
                          String state, String responseType, String userAgent, HttpClientConfig httpClientConfig,
                          HttpClient httpClient) {
        super(apiKey, apiSecret, callback, scope, userAgent, httpClientConfig, httpClient);
        this.responseType = responseType;
        this.state = state;
        this.api = api;
    }

    protected OAuth2AccessToken sendAccessTokenRequestSync(OAuthRequest request)
            throws IOException, InterruptedException, ExecutionException {
        return api.getAccessTokenExtractor().extract(execute(request));
    }

    protected Future<OAuth2AccessToken> sendAccessTokenRequestAsync(OAuthRequest request,
            OAuthAsyncRequestCallback<OAuth2AccessToken> callback) {

        return execute(request, callback, new OAuthRequest.ResponseConverter<OAuth2AccessToken>() {
            @Override
            public OAuth2AccessToken convert(Response response) throws IOException {
                return getApi().getAccessTokenExtractor().extract(response);
            }
        });
    }

    public OAuth2AccessToken getAccessToken(String code) throws IOException, InterruptedException, ExecutionException {
        return getAccessToken(code, (String) null);
    }

    public OAuth2AccessToken getAccessToken(String code, String pkceCodeVerifier)
            throws IOException, InterruptedException, ExecutionException {
        final OAuthRequest request = createAccessTokenRequest(code, pkceCodeVerifier);

        return sendAccessTokenRequestSync(request);
    }

    public Future<OAuth2AccessToken> getAccessToken(String code, OAuthAsyncRequestCallback<OAuth2AccessToken> callback,
            String pkceCodeVerifier) {
        final OAuthRequest request = createAccessTokenRequest(code, pkceCodeVerifier);

        return sendAccessTokenRequestAsync(request, callback);
    }

    public Future<OAuth2AccessToken> getAccessToken(String code,
            OAuthAsyncRequestCallback<OAuth2AccessToken> callback) {

        return getAccessToken(code, callback, null);
    }

    protected OAuthRequest createAccessTokenRequest(String code) {
        final OAuthRequest request = new OAuthRequest(api.getAccessTokenHttpMethod(), api.getAccessTokenEndpoint());

        api.getClientAuthentication().addClientAuthentication(request, getApiKey(), getApiSecret());

        request.addParameter(OAuthConstants.CODE, code);
        final String callback = getCallback();
        if (callback != null) {
            request.addParameter(OAuthConstants.REDIRECT_URI, callback);
        }
        final String scope = getScope();
        if (scope != null) {
            request.addParameter(OAuthConstants.SCOPE, scope);
        }
        request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.AUTHORIZATION_CODE);
        return request;
    }

    protected OAuthRequest createAccessTokenRequest(String code, String pkceCodeVerifier) {
        final OAuthRequest request = createAccessTokenRequest(code);
        if (pkceCodeVerifier != null) {
            request.addParameter(PKCE.PKCE_CODE_VERIFIER_PARAM, pkceCodeVerifier);
        }
        return request;
    }

    public Future<OAuth2AccessToken> refreshAccessTokenAsync(String refreshToken) {
        return refreshAccessToken(refreshToken, null);
    }

    public OAuth2AccessToken refreshAccessToken(String refreshToken)
            throws IOException, InterruptedException, ExecutionException {
        final OAuthRequest request = createRefreshTokenRequest(refreshToken);

        return sendAccessTokenRequestSync(request);
    }

    public Future<OAuth2AccessToken> refreshAccessToken(String refreshToken,
            OAuthAsyncRequestCallback<OAuth2AccessToken> callback) {
        final OAuthRequest request = createRefreshTokenRequest(refreshToken);

        return sendAccessTokenRequestAsync(request, callback);
    }

    protected OAuthRequest createRefreshTokenRequest(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new IllegalArgumentException("The refreshToken cannot be null or empty");
        }
        final OAuthRequest request = new OAuthRequest(api.getAccessTokenHttpMethod(), api.getRefreshTokenEndpoint());

        api.getClientAuthentication().addClientAuthentication(request, getApiKey(), getApiSecret());

        final String scope = getScope();
        if (scope != null) {
            request.addParameter(OAuthConstants.SCOPE, scope);
        }

        request.addParameter(OAuthConstants.REFRESH_TOKEN, refreshToken);
        request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.REFRESH_TOKEN);
        return request;
    }

    public OAuth2AccessToken getAccessTokenPasswordGrant(String uname, String password)
            throws IOException, InterruptedException, ExecutionException {
        final OAuthRequest request = createAccessTokenPasswordGrantRequest(uname, password);

        return sendAccessTokenRequestSync(request);
    }

    public Future<OAuth2AccessToken> getAccessTokenPasswordGrantAsync(String uname, String password) {
        return getAccessTokenPasswordGrantAsync(uname, password, null);
    }

    public Future<OAuth2AccessToken> getAccessTokenPasswordGrantAsync(String uname, String password,
            OAuthAsyncRequestCallback<OAuth2AccessToken> callback) {
        final OAuthRequest request = createAccessTokenPasswordGrantRequest(uname, password);

        return sendAccessTokenRequestAsync(request, callback);
    }

    protected OAuthRequest createAccessTokenPasswordGrantRequest(String username, String password) {
        final OAuthRequest request = new OAuthRequest(api.getAccessTokenHttpMethod(), api.getAccessTokenEndpoint());
        request.addParameter(OAuthConstants.USERNAME, username);
        request.addParameter(OAuthConstants.PASSWORD, password);

        final String scope = getScope();
        if (scope != null) {
            request.addParameter(OAuthConstants.SCOPE, scope);
        }

        request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.PASSWORD);

        api.getClientAuthentication().addClientAuthentication(request, getApiKey(), getApiSecret());

        return request;
    }

    public Future<OAuth2AccessToken> getAccessTokenClientCredentialsGrantAsync() {
        return getAccessTokenClientCredentialsGrant(null);
    }

    public OAuth2AccessToken getAccessTokenClientCredentialsGrant()
            throws IOException, InterruptedException, ExecutionException {
        final OAuthRequest request = createAccessTokenClientCredentialsGrantRequest();

        return sendAccessTokenRequestSync(request);
    }

    public Future<OAuth2AccessToken> getAccessTokenClientCredentialsGrant(
            OAuthAsyncRequestCallback<OAuth2AccessToken> callback) {
        final OAuthRequest request = createAccessTokenClientCredentialsGrantRequest();

        return sendAccessTokenRequestAsync(request, callback);
    }

    protected OAuthRequest createAccessTokenClientCredentialsGrantRequest() {
        final OAuthRequest request = new OAuthRequest(api.getAccessTokenHttpMethod(), api.getAccessTokenEndpoint());

        api.getClientAuthentication().addClientAuthentication(request, getApiKey(), getApiSecret());

        final String scope = getScope();
        if (scope != null) {
            request.addParameter(OAuthConstants.SCOPE, scope);
        }
        request.addParameter(OAuthConstants.GRANT_TYPE, OAuthConstants.CLIENT_CREDENTIALS);
        return request;
    }

    @Override
    public String getVersion() {
        return VERSION;
    }

    public void signRequest(String accessToken, OAuthRequest request) {
        api.getBearerSignature().signRequest(accessToken, request);
    }

    public void signRequest(OAuth2AccessToken accessToken, OAuthRequest request) {
        signRequest(accessToken == null ? null : accessToken.getAccessToken(), request);
    }

    public AuthorizationUrlWithPKCE getAuthorizationUrlWithPKCE() throws NoSuchAlgorithmException {
        return getAuthorizationUrlWithPKCE(null);
    }

    public AuthorizationUrlWithPKCE getAuthorizationUrlWithPKCE(Map<String, String> additionalParams) throws NoSuchAlgorithmException {
        final PKCE pkce = PKCE_SERVICE.generatePKCE();
        return new AuthorizationUrlWithPKCE(pkce, getAuthorizationUrl(additionalParams, pkce));
    }

    public String getAuthorizationUrl() {
        return getAuthorizationUrl(null, null);
    }

    public String getAuthorizationUrl(Map<String, String> additionalParams) {
        return getAuthorizationUrl(additionalParams, null);
    }

    public String getAuthorizationUrl(PKCE pkce) {
        return getAuthorizationUrl(null, pkce);
    }

    public String getAuthorizationUrl(Map<String, String> additionalParams, PKCE pkce) {
        final Map<String, String> params;
        if (pkce == null) {
            params = additionalParams;
        } else {
            params = additionalParams == null ? new HashMap<String, String>() : new HashMap<>(additionalParams);
            params.putAll(pkce.getAuthorizationUrlParams());
        }
        return api.getAuthorizationUrl(getResponseType(), getApiKey(), getCallback(), getScope(), getState(), params);
    }

    public DefaultApi20 getApi() {
        return api;
    }

    protected OAuthRequest createRevokeTokenRequest(String tokenToRevoke, TokenTypeHint tokenTypeHint) {
        final OAuthRequest request = new OAuthRequest(HttpMethod.POST, api.getRevokeTokenEndpoint());

        api.getClientAuthentication().addClientAuthentication(request, getApiKey(), getApiSecret());

        request.addParameter("token", tokenToRevoke);
        if (tokenTypeHint != null) {
            request.addParameter("token_type_hint", tokenTypeHint.toString());
        }
        return request;
    }

    public Future<Void> revokeTokenAsync(String tokenToRevoke) {
        return revokeTokenAsync(tokenToRevoke, null);
    }

    public Future<Void> revokeTokenAsync(String tokenToRevoke, TokenTypeHint tokenTypeHint) {
        return revokeToken(tokenToRevoke, null, tokenTypeHint);
    }

    public void revokeToken(String tokenToRevoke) throws IOException, InterruptedException, ExecutionException {
        revokeToken(tokenToRevoke, (TokenTypeHint) null);
    }

    public void revokeToken(String tokenToRevoke, TokenTypeHint tokenTypeHint)
            throws IOException, InterruptedException, ExecutionException {
        final OAuthRequest request = createRevokeTokenRequest(tokenToRevoke, tokenTypeHint);

        checkForErrorRevokeToken(execute(request));
    }

    public Future<Void> revokeToken(String tokenToRevoke, OAuthAsyncRequestCallback<Void> callback) {
        return revokeToken(tokenToRevoke, callback, null);
    }

    public Future<Void> revokeToken(String tokenToRevoke, OAuthAsyncRequestCallback<Void> callback,
            TokenTypeHint tokenTypeHint) {
        final OAuthRequest request = createRevokeTokenRequest(tokenToRevoke, tokenTypeHint);

        return execute(request, callback, new OAuthRequest.ResponseConverter<Void>() {
            @Override
            public Void convert(Response response) throws IOException {
                checkForErrorRevokeToken(response);
                return null;
            }
        });
    }

    private void checkForErrorRevokeToken(Response response) throws IOException {
        if (response.getCode() != 200) {
            OAuth2AccessTokenJsonExtractor.instance().generateError(response.getBody());
        }
    }

    public OAuth2Authorization extractAuthorization(String redirectLocation) {
        final OAuth2Authorization authorization = new OAuth2Authorization();
        int end = redirectLocation.indexOf('#');
        if (end == -1) {
            end = redirectLocation.length();
        }
        for (String param : redirectLocation.substring(redirectLocation.indexOf('?') + 1, end).split("&")) {
            final String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                switch (keyValue[0]) {
                    case "code":
                        authorization.setCode(keyValue[1]);
                        break;
                    case "state":
                        authorization.setState(keyValue[1]);
                        break;
                    default:
                }
            }
        }
        return authorization;
    }

    public String getResponseType() {
        return responseType;
    }

    public String getState() {
        return state;
    }
}
