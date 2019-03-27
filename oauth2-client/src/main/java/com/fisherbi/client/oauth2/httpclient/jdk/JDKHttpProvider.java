package com.fisherbi.client.oauth2.httpclient.jdk;


import com.fisherbi.client.oauth2.httpclient.HttpClient;
import com.fisherbi.client.oauth2.httpclient.HttpClientConfig;
import com.fisherbi.client.oauth2.httpclient.HttpClientProvider;

public class JDKHttpProvider implements HttpClientProvider {

    @Override
    public HttpClient createClient(HttpClientConfig config) {
        if (config instanceof JDKHttpClientConfig) {
            return new JDKHttpClient((JDKHttpClientConfig) config);
        }
        return null;
    }
}
