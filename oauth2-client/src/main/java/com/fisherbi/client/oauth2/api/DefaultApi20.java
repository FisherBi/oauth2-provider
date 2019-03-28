package com.fisherbi.client.oauth2.api;

import com.fisherbi.client.oauth2.extractor.OAuth2AccessTokenJsonExtractor;
import com.fisherbi.client.oauth2.extractor.TokenExtractor;
import com.fisherbi.client.oauth2.httpclient.HttpClient;
import com.fisherbi.client.oauth2.httpclient.HttpClientConfig;
import com.fisherbi.client.oauth2.model.HttpMethod;
import com.fisherbi.client.oauth2.model.OAuth2AccessToken;
import com.fisherbi.client.oauth2.model.OAuthConstants;
import com.fisherbi.client.oauth2.model.ParameterList;
import com.fisherbi.client.oauth2.service.OAuth20Service;
import com.fisherbi.client.oauth2.service.bearersignature.BearerSignature;
import com.fisherbi.client.oauth2.service.bearersignature.BearerSignatureAuthorizationRequestHeaderField;
import com.fisherbi.client.oauth2.service.clientauthentication.ClientAuthentication;
import com.fisherbi.client.oauth2.service.clientauthentication.HttpBasicAuthenticationScheme;

import java.io.OutputStream;
import java.util.Map;

public abstract class DefaultApi20 implements BaseApi<OAuth20Service> {

    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return OAuth2AccessTokenJsonExtractor.instance();
    }

    public HttpMethod getAccessTokenHttpMethod() {
        return HttpMethod.POST;
    }

    public abstract String getAccessTokenEndpoint();

    public String getRefreshTokenEndpoint() {
        return getAccessTokenEndpoint();
    }

    public String getRevokeTokenEndpoint() {
        throw new UnsupportedOperationException(
                "This API doesn't support revoking tokens or we have no info about this");
    }

    protected abstract String getAuthorizationBaseUrl();

    public String getAuthorizationUrl(String responseType, String apiKey, String callback, String scope, String state,
            Map<String, String> additionalParams) {
        final ParameterList parameters = new ParameterList(additionalParams);
        parameters.add(OAuthConstants.RESPONSE_TYPE, responseType);
        parameters.add(OAuthConstants.CLIENT_ID, apiKey);

        if (callback != null) {
            parameters.add(OAuthConstants.REDIRECT_URI, callback);
        }

        if (scope != null) {
            parameters.add(OAuthConstants.SCOPE, scope);
        }

        if (state != null) {
            parameters.add(OAuthConstants.STATE, state);
        }

        return parameters.appendTo(getAuthorizationBaseUrl());
    }

    @Override
    public OAuth20Service createService(String apiKey, String apiSecret, String callback, String scope,
                                        OutputStream debugStream, String state, String responseType, String userAgent,
                                        HttpClientConfig httpClientConfig, HttpClient httpClient) {
        return new OAuth20Service(this, apiKey, apiSecret, callback, scope, state, responseType, userAgent,
                httpClientConfig, httpClient);
    }

    public BearerSignature getBearerSignature() {
        return BearerSignatureAuthorizationRequestHeaderField.instance();
    }

    public ClientAuthentication getClientAuthentication() {
        return HttpBasicAuthenticationScheme.instance();
    }
}
