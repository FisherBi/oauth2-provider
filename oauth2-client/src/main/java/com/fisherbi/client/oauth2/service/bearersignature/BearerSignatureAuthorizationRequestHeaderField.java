package com.fisherbi.client.oauth2.service.bearersignature;

import com.fisherbi.client.oauth2.model.OAuthConstants;
import com.fisherbi.client.oauth2.model.OAuthRequest;


public class BearerSignatureAuthorizationRequestHeaderField implements BearerSignature {

    protected BearerSignatureAuthorizationRequestHeaderField() {
    }

    private static class InstanceHolder {

        private static final BearerSignatureAuthorizationRequestHeaderField INSTANCE
                = new BearerSignatureAuthorizationRequestHeaderField();
    }

    public static BearerSignatureAuthorizationRequestHeaderField instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void signRequest(String accessToken, OAuthRequest request) {
        request.addHeader(OAuthConstants.HEADER, "Bearer " + accessToken);
    }
}
