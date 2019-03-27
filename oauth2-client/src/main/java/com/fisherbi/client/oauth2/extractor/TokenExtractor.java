package com.fisherbi.client.oauth2.extractor;


import com.fisherbi.client.oauth2.exception.OAuthException;
import com.fisherbi.client.oauth2.model.Response;
import com.fisherbi.client.oauth2.model.Token;

import java.io.IOException;

/**
 * Simple command object that extracts a concrete {@link Token} from a String
 * @param <T> concrete type of Token
 */
public interface TokenExtractor<T extends Token> {

    /**
     * Extracts the concrete type of token from the contents of an Http Response
     *
     * @param response the whole response
     * @return OAuth access token
     * @throws IOException in case of troubles while getting body from the response
     */
    T extract(Response response) throws IOException, OAuthException;
}
