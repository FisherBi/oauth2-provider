package com.fisherbi.client.oauth2.model;

public interface OAuthAsyncRequestCallback<T> {

    void onCompleted(T response);

    void onThrowable(Throwable t);
}
