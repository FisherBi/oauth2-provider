package com.fisherbi.client.oauth2.service.bearersignature;


import com.fisherbi.client.oauth2.model.OAuthRequest;

public interface BearerSignature {
    void signRequest(String accessToken, OAuthRequest request);
}
