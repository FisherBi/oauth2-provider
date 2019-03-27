package com.fisherbi.client.oauth2.httpclient;

public interface HttpClientProvider {

    HttpClient createClient(HttpClientConfig httpClientConfig);
}
