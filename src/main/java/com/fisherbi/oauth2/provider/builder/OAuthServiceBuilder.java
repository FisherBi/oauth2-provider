package com.fisherbi.oauth2.provider.builder;

import httpclient.HttpClient;
import httpclient.HttpClientConfig;

import java.io.OutputStream;

/**
 * Created by FisherBi on 2019/1/28.
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
