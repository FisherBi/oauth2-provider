package com.fisherbi.client.oauth2.builder;

import com.fisherbi.client.oauth2.httpclient.HttpClientConfig;
import com.fisherbi.client.oauth2.httpclient.HttpClient;
import com.fisherbi.client.oauth2.api.BaseApi;
import com.fisherbi.client.oauth2.service.OAuthService;
import com.fisherbi.client.oauth2.utils.Preconditions;

import java.io.OutputStream;

/**
 * 创造OAuthService类的Builder类
 */
public class OAuthServiceBuilder {
    private String callback;
    private String apiKey;
    private String apiSecret;
    private String scope;
    private String state;
    private OutputStream debugStream;
    private String responseType = "code";
    private String userAgent;

    private HttpClientConfig httpClientConfig;
    private HttpClient httpClient;

    public OAuthServiceBuilder(String apiKey) {
        apiKey(apiKey);
    }

    public final OAuthServiceBuilder apiKey(String apiKey) {
        Preconditions.checkEmptyString(apiKey, "Invalid Api key");
        this.apiKey = apiKey;
        return this;
    }

    public OAuthServiceBuilder apiSecret(String apiSecret) {
        Preconditions.checkEmptyString(apiSecret, "Invalid Api secret");
        this.apiSecret = apiSecret;
        return this;
    }

    public OAuthServiceBuilder state(String state) {
        Preconditions.checkEmptyString(state, "Invalid OAuth state");
        this.state = state;
        return this;
    }

    public OAuthServiceBuilder callback(String callback) {
        this.callback = callback;
        return this;
    }

    public <S extends OAuthService> S build(BaseApi<S> api) {
        return api.createService(apiKey, apiSecret, callback, scope, debugStream, state, responseType, userAgent,
                httpClientConfig, httpClient);
    }
}
