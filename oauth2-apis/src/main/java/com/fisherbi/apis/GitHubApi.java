package com.fisherbi.apis;

import com.fisherbi.client.oauth2.api.DefaultApi20;
import com.fisherbi.client.oauth2.extractor.OAuth2AccessTokenExtractor;
import com.fisherbi.client.oauth2.extractor.TokenExtractor;
import com.fisherbi.client.oauth2.model.HttpMethod;
import com.fisherbi.client.oauth2.model.OAuth2AccessToken;

public class GitHubApi extends DefaultApi20 {

    protected GitHubApi() {
    }

    private static class InstanceHolder {
        private static final GitHubApi INSTANCE = new GitHubApi();
    }

    public static GitHubApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public HttpMethod getAccessTokenHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://github.com/login/oauth/access_token";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "https://github.com/login/oauth/authorize";
    }

    @Override
    public TokenExtractor<OAuth2AccessToken> getAccessTokenExtractor() {
        return OAuth2AccessTokenExtractor.instance();
    }
}
