package com.fisherbi.client.oauth2.httpclient.jdk;

import com.fisherbi.client.oauth2.exception.OAuthException;
import com.fisherbi.client.oauth2.httpclient.HttpClient;
import com.fisherbi.client.oauth2.httpclient.multipart.BodyPartPayload;
import com.fisherbi.client.oauth2.httpclient.multipart.ByteArrayBodyPartPayload;
import com.fisherbi.client.oauth2.httpclient.multipart.MultipartPayload;
import com.fisherbi.client.oauth2.model.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class JDKHttpClient implements HttpClient {

    private final JDKHttpClientConfig config;

    public JDKHttpClient() {
        this(JDKHttpClientConfig.defaultConfig());
    }

    public JDKHttpClient(JDKHttpClientConfig clientConfig) {
        config = clientConfig;
    }

    @Override
    public void close() {
    }

    @Override
    public <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpMethod, String completeUrl,
                                      byte[] bodyContents, OAuthAsyncRequestCallback<T> callback, OAuthRequest.ResponseConverter<T> converter) {

        return doExecuteAsync(userAgent, headers, httpMethod, completeUrl, BodyType.BYTE_ARRAY, bodyContents, callback,
                converter);
    }


    @Override
    public <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
            MultipartPayload bodyContents, OAuthAsyncRequestCallback<T> callback,
            OAuthRequest.ResponseConverter<T> converter) {

        return doExecuteAsync(userAgent, headers, httpHttpMethod, completeUrl, BodyType.MULTIPART, bodyContents, callback,
                converter);
    }

    @Override
    public <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
            String bodyContents, OAuthAsyncRequestCallback<T> callback, OAuthRequest.ResponseConverter<T> converter) {

        return doExecuteAsync(userAgent, headers, httpHttpMethod, completeUrl, BodyType.STRING, bodyContents, callback,
                converter);
    }

    @Override
    public <T> Future<T> executeAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
            File bodyContents, OAuthAsyncRequestCallback<T> callback, OAuthRequest.ResponseConverter<T> converter) {
        throw new UnsupportedOperationException("JDKHttpClient does not support File payload for the moment");
    }

    private <T> Future<T> doExecuteAsync(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod,
            String completeUrl, BodyType bodyType, Object bodyContents, OAuthAsyncRequestCallback<T> callback,
            OAuthRequest.ResponseConverter<T> converter) {
        try {
            final Response response = doExecute(userAgent, headers, httpHttpMethod, completeUrl, bodyType, bodyContents);
            @SuppressWarnings("unchecked")
            final T t = converter == null ? (T) response : converter.convert(response);
            if (callback != null) {
                callback.onCompleted(t);
            }
            return new JDKHttpFuture<>(t);
        } catch (IOException | RuntimeException e) {
            if (callback != null) {
                callback.onThrowable(e);
            }
            return new JDKHttpFuture<>(e);
        }
    }

    @Override
    public Response execute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
            byte[] bodyContents) throws InterruptedException, ExecutionException, IOException {
        return doExecute(userAgent, headers, httpHttpMethod, completeUrl, BodyType.BYTE_ARRAY, bodyContents);
    }

    @Override
    public Response execute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
            MultipartPayload multipartPayloads) throws InterruptedException, ExecutionException, IOException {
        return doExecute(userAgent, headers, httpHttpMethod, completeUrl, BodyType.MULTIPART, multipartPayloads);
    }

    @Override
    public Response execute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
            String bodyContents) throws InterruptedException, ExecutionException, IOException {
        return doExecute(userAgent, headers, httpHttpMethod, completeUrl, BodyType.STRING, bodyContents);
    }

    @Override
    public Response execute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
            File bodyContents) throws InterruptedException, ExecutionException, IOException {
        throw new UnsupportedOperationException("JDKHttpClient does not support File payload for the moment");
    }

    private Response doExecute(String userAgent, Map<String, String> headers, HttpMethod httpHttpMethod, String completeUrl,
            BodyType bodyType, Object bodyContents) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(completeUrl).openConnection();
        connection.setInstanceFollowRedirects(config.isFollowRedirects());
        connection.setRequestMethod(httpHttpMethod.name());
        if (config.getConnectTimeout() != null) {
            connection.setConnectTimeout(config.getConnectTimeout());
        }
        if (config.getReadTimeout() != null) {
            connection.setReadTimeout(config.getReadTimeout());
        }
        addHeaders(connection, headers, userAgent);
        if (httpHttpMethod.isPermitBody()) {
            bodyType.setBody(connection, bodyContents, httpHttpMethod.isRequiresBody());
        }

        try {
            connection.connect();
            final int responseCode = connection.getResponseCode();
            return new Response(responseCode, connection.getResponseMessage(), parseHeaders(connection),
                    responseCode >= 200 && responseCode < 400 ? connection.getInputStream()
                            : connection.getErrorStream());
        } catch (UnknownHostException e) {
            throw new OAuthException("The IP address of a host could not be determined.", e);
        }
    }

    private enum BodyType {
        BYTE_ARRAY {
            @Override
            void setBody(HttpURLConnection connection, Object bodyContents, boolean requiresBody) throws IOException {
                addBody(connection, (byte[]) bodyContents, requiresBody);
            }
        },
        MULTIPART {
            @Override
            void setBody(HttpURLConnection connection, Object bodyContents, boolean requiresBody) throws IOException {
                addBody(connection, (MultipartPayload) bodyContents, requiresBody);
            }
        },
        STRING {
            @Override
            void setBody(HttpURLConnection connection, Object bodyContents, boolean requiresBody) throws IOException {
                addBody(connection, ((String) bodyContents).getBytes(), requiresBody);
            }
        };

        abstract void setBody(HttpURLConnection connection, Object bodyContents, boolean requiresBody)
                throws IOException;
    }

    private static Map<String, String> parseHeaders(HttpURLConnection conn) {
        final Map<String, String> headers = new HashMap<>();

        for (Map.Entry<String, List<String>> headerField : conn.getHeaderFields().entrySet()) {
            final String key = headerField.getKey();
            final String value = headerField.getValue().get(0);
            if ("Content-Encoding".equalsIgnoreCase(key)) {
                headers.put("Content-Encoding", value);
            } else {
                headers.put(key, value);
            }
        }
        return headers;
    }

    private static void addHeaders(HttpURLConnection connection, Map<String, String> headers, String userAgent) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        if (userAgent != null) {
            connection.setRequestProperty(OAuthConstants.USER_AGENT_HEADER_NAME, userAgent);
        }
    }

    private static void addBody(HttpURLConnection connection, byte[] content, boolean requiresBody) throws IOException {
        final int contentLength = content.length;
        if (requiresBody || contentLength > 0) {
            prepareConnectionForBodyAndGetOutputStream(connection, contentLength).write(content);
        }
    }


    private static void addBody(HttpURLConnection connection, MultipartPayload multipartPayload, boolean requiresBody)
            throws IOException {

        for (Map.Entry<String, String> header : multipartPayload.getHeaders().entrySet()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        if (requiresBody) {
            final ByteArrayOutputStream os = getPayload(multipartPayload);

            if (os.size() > 0) {
                os.writeTo(prepareConnectionForBodyAndGetOutputStream(connection, os.size()));
            }
        }
    }

    static ByteArrayOutputStream getPayload(MultipartPayload multipartPayload) throws IOException {
        final ByteArrayOutputStream os = new ByteArrayOutputStream();

        final String preamble = multipartPayload.getPreamble();
        if (preamble != null) {
            os.write(preamble.getBytes());
        }
        final List<BodyPartPayload> bodyParts = multipartPayload.getBodyParts();
        if (!bodyParts.isEmpty()) {
            final String boundary = multipartPayload.getBoundary();
            final byte[] startBoundary = ("\r\n--" + boundary + "\r\n").getBytes();

            for (BodyPartPayload bodyPart : bodyParts) {
                os.write(startBoundary);

                final Map<String, String> bodyPartHeaders = bodyPart.getHeaders();
                if (bodyPartHeaders != null) {
                    for (Map.Entry<String, String> header : bodyPartHeaders.entrySet()) {
                        os.write((header.getKey() + ": " + header.getValue() + "\r\n").getBytes());
                    }
                }

                if (bodyPart instanceof MultipartPayload) {
                    getPayload((MultipartPayload) bodyPart).writeTo(os);
                } else if (bodyPart instanceof ByteArrayBodyPartPayload) {
                    os.write("\r\n".getBytes());
                    os.write(((ByteArrayBodyPartPayload) bodyPart).getPayload());
                } else {
                    throw new AssertionError(bodyPart.getClass());
                }
            }

            os.write(("\r\n--" + boundary + "--\r\n").getBytes());
            final String epilogue = multipartPayload.getEpilogue();
            if (epilogue != null) {
                os.write((epilogue + "\r\n").getBytes());
            }

        }
        return os;
    }

    private static OutputStream prepareConnectionForBodyAndGetOutputStream(HttpURLConnection connection,
            int contentLength) throws IOException {

        connection.setRequestProperty(CONTENT_LENGTH, String.valueOf(contentLength));
        if (connection.getRequestProperty(CONTENT_TYPE) == null) {
            connection.setRequestProperty(CONTENT_TYPE, DEFAULT_CONTENT_TYPE);
        }
        connection.setDoOutput(true);
        return connection.getOutputStream();
    }

}
