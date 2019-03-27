package com.fisherbi.client.oauth2.service.clientauthentication;

import com.fisherbi.client.oauth2.model.OAuthRequest;

public interface ClientAuthentication {

    void addClientAuthentication(OAuthRequest request, String apiKey, String apiSecret);
}
