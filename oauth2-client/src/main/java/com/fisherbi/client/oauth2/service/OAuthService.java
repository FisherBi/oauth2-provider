package com.fisherbi.client.oauth2.service;

import com.fisherbi.client.oauth2.httpclient.HttpClient;
import com.fisherbi.client.oauth2.httpclient.HttpClientConfig;
import com.fisherbi.client.oauth2.httpclient.HttpClientProvider;
import com.fisherbi.client.oauth2.httpclient.jdk.JDKHttpClient;
import com.fisherbi.client.oauth2.httpclient.jdk.JDKHttpClientConfig;
import com.fisherbi.client.oauth2.model.OAuthAsyncRequestCallback;
import com.fisherbi.client.oauth2.model.OAuthRequest;
import com.fisherbi.client.oauth2.model.Response;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.ServiceLoader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public abstract class OAuthService implements Closeable {
    private final String apiKey;
    private final String apiSecret;
    private final String callback;
    private final String scope;
    private final String userAgent;
    private final HttpClient httpClient;

    public OAuthService(String apiKey, String apiSecret, String callback, String scope, String userAgent,
                        HttpClientConfig httpClientConfig, HttpClient httpClient) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.callback = callback;
        this.scope = scope;
        this.userAgent = userAgent;

        if (httpClientConfig == null && httpClient == null) {
            this.httpClient = new JDKHttpClient(JDKHttpClientConfig.defaultConfig());
        } else {
            this.httpClient = httpClient == null ? getClient(httpClientConfig) : httpClient;
        }
    }

    private static HttpClient getClient(HttpClientConfig config) {
        for (HttpClientProvider provider : ServiceLoader.load(HttpClientProvider.class)) {
            final HttpClient client = provider.createClient(config);
            if (client != null) {
                return client;
            }
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public String getCallback() {
        return callback;
    }

    public String getScope() {
        return scope;
    }
    
    public abstract String getVersion();

    public Future<Response> executeAsync(OAuthRequest request) {
        return execute(request, null);
    }

    public Future<Response> execute(OAuthRequest request, OAuthAsyncRequestCallback<Response> callback) {
        return execute(request, callback, null);
    }

    public <R> Future<R> execute(OAuthRequest request, OAuthAsyncRequestCallback<R> callback,
                                 OAuthRequest.ResponseConverter<R> converter) {

        final File filePayload = request.getFilePayload();
        if (filePayload != null) {
            return httpClient.executeAsync(userAgent, request.getHeaders(), request.getHttpMethod(), request.getCompleteUrl(),
                    filePayload, callback, converter);
        } else if (request.getStringPayload() != null) {
            return httpClient.executeAsync(userAgent, request.getHeaders(), request.getHttpMethod(), request.getCompleteUrl(),
                    request.getStringPayload(), callback, converter);
        } else {
            return httpClient.executeAsync(userAgent, request.getHeaders(), request.getHttpMethod(), request.getCompleteUrl(),
                    request.getByteArrayPayload(), callback, converter);
        }
    }

    public Response execute(OAuthRequest request) throws InterruptedException, ExecutionException, IOException {
        final File filePayload = request.getFilePayload();
        if (filePayload != null) {
            return httpClient.execute(userAgent, request.getHeaders(), request.getHttpMethod(), request.getCompleteUrl(),
                    filePayload);
        } else if (request.getStringPayload() != null) {
            return httpClient.execute(userAgent, request.getHeaders(), request.getHttpMethod(), request.getCompleteUrl(),
                    request.getStringPayload());
        } else if (request.getMultipartPayload() != null) {
            return httpClient.execute(userAgent, request.getHeaders(), request.getHttpMethod(), request.getCompleteUrl(),
                    request.getMultipartPayload());
        } else {
            return httpClient.execute(userAgent, request.getHeaders(), request.getHttpMethod(), request.getCompleteUrl(),
                    request.getByteArrayPayload());
        }
    }
}
