package com.fisherbi.client.oauth2.revoke;

import com.fisherbi.client.oauth2.extractor.OAuth2AccessTokenJsonExtractor;
import com.fisherbi.client.oauth2.model.Response;

import java.io.IOException;

public class OAuth2RevokeTokenResponseConverter {

    public Void convert(Response response) throws IOException {
        if (response.getCode() != 200) {
            OAuth2AccessTokenJsonExtractor.instance().generateError(response.getBody());
        }
        return null;
    }
}
