package com.fisherbi.client.oauth2.extractor;


import com.fisherbi.client.oauth2.exception.OAuthException;
import com.fisherbi.client.oauth2.model.Response;
import com.fisherbi.client.oauth2.model.Token;

import java.io.IOException;

public interface TokenExtractor<T extends Token> {

    T extract(Response response) throws IOException, OAuthException;
}
