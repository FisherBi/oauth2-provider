package com.fisherbi.client.oauth2.service.clientauthentication;


import com.fisherbi.client.oauth2.model.OAuthConstants;
import com.fisherbi.client.oauth2.model.OAuthRequest;
import com.fisherbi.client.oauth2.utils.Base64;

import java.nio.charset.Charset;

public class HttpBasicAuthenticationScheme implements ClientAuthentication {

    private final Base64.Encoder base64Encoder = Base64.getEncoder();

    protected HttpBasicAuthenticationScheme() {
    }

    private static class InstanceHolder {

        private static final HttpBasicAuthenticationScheme INSTANCE = new HttpBasicAuthenticationScheme();
    }

    public static HttpBasicAuthenticationScheme instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public void addClientAuthentication(OAuthRequest request, String apiKey, String apiSecret) {
        if (apiKey != null && apiSecret != null) {
            request.addHeader(OAuthConstants.HEADER, OAuthConstants.BASIC + ' '
                    + base64Encoder.encodeToString(
                            String.format("%s:%s", apiKey, apiSecret).getBytes(Charset.forName("UTF-8"))));
        }
    }

}
