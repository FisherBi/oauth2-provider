package com.fisherbi.client.oauth2.api;

import com.fisherbi.client.oauth2.httpclient.HttpClient;
import com.fisherbi.client.oauth2.httpclient.HttpClientConfig;
import com.fisherbi.client.oauth2.service.OAuthService;

import java.io.OutputStream;

public interface BaseApi<T extends OAuthService> {

    T createService(String apiKey, String apiSecret, String callback, String scope, OutputStream debugStream,
                    String state, String responseType, String userAgent, HttpClientConfig httpClientConfig,
                    HttpClient httpClient);
}
