package com.fisherbi.client.oauth2.service.bearersignature;

import com.fisherbi.client.oauth2.model.OAuthConstants;
import com.fisherbi.client.oauth2.model.OAuthRequest;

public class BearerSignatureURIQueryParameter implements BearerSignature {
    protected BearerSignatureURIQueryParameter() {
    }

    private static class InstanceHolder {

        private static final BearerSignatureURIQueryParameter INSTANCE = new BearerSignatureURIQueryParameter();
    }

    public static BearerSignatureURIQueryParameter instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void signRequest(String accessToken, OAuthRequest request) {
        request.addQuerystringParameter(OAuthConstants.ACCESS_TOKEN, accessToken);
    }
}
