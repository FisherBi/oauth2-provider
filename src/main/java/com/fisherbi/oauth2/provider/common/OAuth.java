package com.fisherbi.oauth2.provider.common;

import com.fisherbi.oauth2.provider.common.message.types.ParameterStyle;
import com.fisherbi.oauth2.provider.common.message.types.TokenType;
import io.jsonwebtoken.Claims;

/**
 * Created by fisbii on 18-8-3.
 */
public final class OAuth {
    private OAuth(){}

    public static final class HttpMethod {
        private HttpMethod(){}
        public static final String POST = "POST";
        public static final String GET = "GET";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
    }

    public static final class HeaderType {
        private HeaderType(){}
        public static final String CONTENT_TYPE = "Content-Type";
        public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
        public static final String AUTHORIZATION = "Authorization";
    }

    public static final class WWWAuthHeader {
        private WWWAuthHeader(){}
        public static final String REALM = "realm";
    }

    public static final class ContentType {
        private ContentType(){}
        public static final String URL_ENCODED = "application/x-www-form-urlencoded";
        public static final String JSON = "application/json";
    }

    public static final String OAUTH_RESPONSE_TYPE = "response_type";
    public static final String OAUTH_CLIENT_ID = "client_id";
    public static final String OAUTH_CLIENT_SECRET = "client_secret";
    public static final String OAUTH_REDIRECT_URI = "redirect_uri";
    public static final String OAUTH_USERNAME = "username";
    public static final String OAUTH_PASSWORD = "password";
    public static final String OAUTH_ASSERTION_TYPE = "assertion_type";
    public static final String OAUTH_ASSERTION = "assertion";
    public static final String OAUTH_SCOPE = "scope";
    public static final String OAUTH_STATE = "state";
    public static final String OAUTH_GRANT_TYPE = "grant_type";

    public static final String OAUTH_HEADER_NAME = "Bearer";

    //Authorization response params
    public static final String OAUTH_CODE = "code";
    public static final String OAUTH_ACCESS_TOKEN = "access_token";
    public static final String OAUTH_EXPIRES_IN = "expires_in";
    public static final String OAUTH_REFRESH_TOKEN = "refresh_token";

    public static final String OAUTH_TOKEN_TYPE = "token_type";

    public static final String OAUTH_OAUTH_TOKEN = "oauth_token";

    public static final String OAUTH_BEARER_TOKEN = "access_token";

    public static final ParameterStyle DEFAULT_PARAMETER_STYLE = ParameterStyle.HEADER;
    public static final TokenType DEFAULT_TOKEN_TYPE = TokenType.BEARER;

    public static final String OAUTH_VERSION_DIFFER = "oauth_signature_method";

    public static final String ASSERTION ="assertion";

    //params for revoke
    public static final String OAUTH_TOKEN = "token";
    public static final String OAUTH_TOKEN_TYPE_HINT = "token_type_hint";
    public static final String OAUTH_CALLBACK = "callback";

    //parasm for token info
    public static final String OAUTH_TOKEN_INFO_SCOPE ="_admin:token:introspect";
    public static final String OAUTH_TOKEN_INFO_ACTIVE ="active";
    public static final String OAUTH_TOKEN_INFO_CLIENT_ID = OAUTH_CLIENT_ID;
    public static final String OAUTH_TOKEN_INFO_TOKEN_TYPE = OAUTH_TOKEN_TYPE;
    public static final String OAUTH_TOKEN_INFO_INDIVIDUAL = "individual";
    public static final String OAUTH_TOKEN_INFO_CSR = "csr";
    public static final String OAUTH_TOKEN_INFO_ISS = Claims.ISSUER;
    public static final String OAUTH_TOKEN_INFO_SUB = Claims.SUBJECT;
    public static final String OAUTH_TOKEN_INFO_AUD = Claims.AUDIENCE;
    public static final String OAUTH_TOKEN_INFO_EXP = Claims.EXPIRATION;
    public static final String OAUTH_TOKEN_INFO_IAT = Claims.ISSUED_AT;
    public static final String OAUTH_TOKEN_INFO_NBF = Claims.NOT_BEFORE;
    public static final String OAUTH_TOKEN_INFO_JTI = Claims.ID;


    //params for Authorization Server Metadata
    /**https://tools.ietf.org/html/draft-ietf-oauth-discovery-08#section-2*/
    public static final String OAUTH_AS_METADATA_ISSUER="issuer";
    public static final String AUTHORIZATION_ENDPOINT = "authorization_endpoint";
    public static final String TOKEN_ENDPOINT = "token_endpoint";
    public static final String JWKS_URI ="jwks_uri";
    public static final String USER_INFO_ENDPOINT ="userinfo_endpoint";
    public static final String USER_INFO_ENDPOINT_AUTH_METHODS_SUPPORTED="userinfo_endpoint_auth_methods_supported";
    public static final String REGISTRATION_ENDPOINT = "registration_endpoint";
    public static final String SCOPES_SUPPORTED = "scopes_supported";
    public static final String RESPONSE_TYPES_SUPPORTED = "response_types_supported";
    public static final String RESPONSE_MODES_SUPPORTED ="response_modes_supported";
    public static final String GRANT_TYPES_SUPPORTED="grant_types_supported";
    public static final String TOKEN_ENDPOINT_AUTH_METHODS_SUPPORTED = "token_endpoint_auth_methods_supported";
    public static final String TOKEN_ENDPOINT_AUTH_SIGNING_ALG_VALUES_SUPPORTED="token_endpoint_auth_signing_alg_values_supported";
    public static final String SERVICE_DOCUMENTATION="service_documentation";
    public static final String UI_LOCALES_SUPPORTED="ui_locales_supported";
    public static final String OP_POLICY_URI="op_policy_uri";
    public static final String OP_TOS_URI="op_tos_uri";
    public static final String REVOCATION_ENDPOINT="revocation_endpoint";
    public static final String REVOCATION_ENDPOINT_AUTH_METHODS_SUPPORTED="revocation_endpoint_auth_methods_supported";
    public static final String REVOCATION_ENDPOINT_AUTH_SIGNING_ALG_VALUES_SUPPORTED="revocation_endpoint_auth_signing_alg_values_supported";
    public static final String INTROSPECTION_ENDPOINT="introspection_endpoint";
    public static final String INTROSPECTION_ENDPOINT_AUTH_METHODS_SUPPORTED="introspection_endpoint_auth_methods_supported";
    public static final String INTROSPECTION_ENDPOINT_AUTH_SIGNING_ALG_VALUES_SUPPORTED="introspection_endpoint_auth_signing_alg_values_supported";
    public static final String CODE_CHALLENGE_METHODS_SUPPORTED="code_challenge_methods_supported";

    //params for ctx().args
    public static final String HTTP_CONTEXT_ARGS_ATTRIBUTE_NAME_FOR_AS ="OAuth2_Token_Post_Map";

    public static final String SCOPE_SEPARATOR = " ";

    public static final String HTTP_HEADER_NAME_TOKEN_EXPIRES_IN = "X-Token-Expires-In";
}
