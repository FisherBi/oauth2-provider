package com.fisherbi.client.oauth2.pkce;


import com.fisherbi.client.oauth2.utils.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public enum PKCECodeChallengeMethod {
    S256 {
        private final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

        @Override
        public String transform2CodeChallenge(String codeVerifier) throws NoSuchAlgorithmException {
            return base64Encoder.encodeToString(
                    MessageDigest.getInstance("SHA-256").digest(codeVerifier.getBytes(StandardCharsets.US_ASCII)));
        }
    },
    plain {
        @Override
        public String transform2CodeChallenge(String codeVerifier) {
            return codeVerifier;
        }
    };

    public abstract String transform2CodeChallenge(String codeVerifier) throws NoSuchAlgorithmException;
}
