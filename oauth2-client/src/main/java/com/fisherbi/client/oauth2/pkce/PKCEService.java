package com.fisherbi.client.oauth2.pkce;


import com.fisherbi.client.oauth2.utils.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PKCEService {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final Base64.Encoder BASE_64_ENCODER = Base64.getUrlEncoder().withoutPadding();

    private final int numberOFOctets;

    public PKCEService(int numberOFOctets) {
        this.numberOFOctets = numberOFOctets;
    }

    public PKCEService() {
        this(32);
    }

    public PKCE generatePKCE() throws NoSuchAlgorithmException {
        final byte[] bytes = new byte[numberOFOctets];
        RANDOM.nextBytes(bytes);
        return generatePKCE(bytes);
    }

    public PKCE generatePKCE(byte[] randomBytes) throws NoSuchAlgorithmException {
        final String codeVerifier = BASE_64_ENCODER.encodeToString(randomBytes);

        final PKCE pkce = new PKCE();
        pkce.setCodeVerifier(codeVerifier);
        try {
            pkce.setCodeChallenge(pkce.getCodeChallengeMethod().transform2CodeChallenge(codeVerifier));
        } catch (NoSuchAlgorithmException nsaE) {
            pkce.setCodeChallengeMethod(PKCECodeChallengeMethod.plain);
            pkce.setCodeChallenge(PKCECodeChallengeMethod.plain.transform2CodeChallenge(codeVerifier));
            throw new NoSuchAlgorithmException();
        }
        return pkce;
    }
}
