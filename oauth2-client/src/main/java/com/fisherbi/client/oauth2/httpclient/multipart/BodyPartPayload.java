package com.fisherbi.client.oauth2.httpclient.multipart;


import com.fisherbi.client.oauth2.httpclient.HttpClient;

import java.util.Collections;
import java.util.Map;

public abstract class BodyPartPayload {

    private final Map<String, String> headers;

    public BodyPartPayload() {
        this((Map<String, String>) null);
    }

    public BodyPartPayload(String contentType) {
        this(contentType == null ? null : Collections.singletonMap(HttpClient.CONTENT_TYPE, contentType));
    }

    public BodyPartPayload(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
