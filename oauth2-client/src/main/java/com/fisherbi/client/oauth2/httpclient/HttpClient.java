package com.fisherbi.client.oauth2.httpclient;

import com.fisherbi.client.oauth2.httpclient.multipart.MultipartPayload;
import com.fisherbi.client.oauth2.model.HttpMethod;
import com.fisherbi.client.oauth2.model.OAuthAsyncRequestCallback;
import com.fisherbi.client.oauth2.model.OAuthRequest;
import com.fisherbi.client.oauth2.model.Response;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public interface HttpClient extends Closeable {

    String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
    String CONTENT_TYPE = "Content-Type";
    String CONTENT_LENGTH = "Content-Length";

    <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                               byte[] bodyContents, OAuthAsyncRequestCallback<T> callback, OAuthRequest.ResponseConverter<T> converter);

    <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                               MultipartPayload bodyContents, OAuthAsyncRequestCallback<T> callback,
                               OAuthRequest.ResponseConverter<T> converter);

    <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                               String bodyContents, OAuthAsyncRequestCallback<T> callback, OAuthRequest.ResponseConverter<T> converter);

    <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                               File bodyContents, OAuthAsyncRequestCallback<T> callback, OAuthRequest.ResponseConverter<T> converter);

    Response execute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                     byte[] bodyContents) throws InterruptedException, ExecutionException, IOException;


    Response execute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                     MultipartPayload bodyContents) throws InterruptedException, ExecutionException, IOException;

    Response execute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                     String bodyContents) throws InterruptedException, ExecutionException, IOException;

    Response execute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                     File bodyContents) throws InterruptedException, ExecutionException, IOException;
}
