package com.fisherbi.client.oauth2.service.clientauthentication;

import com.fisherbi.client.oauth2.model.OAuthConstants;
import com.fisherbi.client.oauth2.model.OAuthRequest;


public class RequestBodyAuthenticationScheme implements ClientAuthentication {

    protected RequestBodyAuthenticationScheme() {
    }

    private static class InstanceHolder {

        private static final RequestBodyAuthenticationScheme INSTANCE = new RequestBodyAuthenticationScheme();
    }

    public static RequestBodyAuthenticationScheme instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void addClientAuthentication(OAuthRequest request, String apiKey, String apiSecret) {
        request.addParameter(OAuthConstants.CLIENT_ID, apiKey);
        if (apiSecret != null) {
            request.addParameter(OAuthConstants.CLIENT_SECRET, apiSecret);
        }
    }
}
