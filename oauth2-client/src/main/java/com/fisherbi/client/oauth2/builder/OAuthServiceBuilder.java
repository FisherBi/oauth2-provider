package com.fisherbi.client.oauth2.builder;

import com.fisherbi.client.oauth2.httpclient.HttpClientConfig;
import com.fisherbi.client.oauth2.httpclient.HttpClient;

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
}
