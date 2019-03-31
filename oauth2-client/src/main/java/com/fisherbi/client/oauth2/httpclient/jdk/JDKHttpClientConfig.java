package com.fisherbi.client.oauth2.httpclient.jdk;


import com.fisherbi.client.oauth2.httpclient.HttpClientConfig;

public class JDKHttpClientConfig implements HttpClientConfig {

    private Integer connectTimeout;
    private Integer readTimeout;
    private boolean followRedirects = true;

    @Override
    public JDKHttpClientConfig createDefaultConfig() {
        return defaultConfig();
    }

    public static JDKHttpClientConfig defaultConfig() {
        return new JDKHttpClientConfig();
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Integer readTimeout) {
        this.readTimeout = readTimeout;
    }

    public boolean isFollowRedirects() {
        return followRedirects;
    }

    public void setFollowRedirects(boolean followRedirects) {
        this.followRedirects = followRedirects;
    }
}
