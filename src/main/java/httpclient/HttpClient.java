package httpclient;

import com.fisherbi.oauth2.provider.model.HttpMethod;
import com.fisherbi.oauth2.provider.model.OAuthAsyncRequestCallback;
import com.fisherbi.oauth2.provider.model.OAuthRequest;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.Future;

public interface HttpClient extends Closeable {

    String DEFAULT_CONTENT_TYPE = "application/x-www-form-urlencoded";
    String CONTENT_TYPE = "Content-Type";
    String CONTENT_LENGTH = "Content-Length";

    <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
                               byte[] bodyContents, OAuthAsyncRequestCallback<T> callback, OAuthRequest.ResponseConverter<T> converter);



}
