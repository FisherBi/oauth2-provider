package com.fisherbi.oauth2.provider.model;

import com.fisherbi.oauth2.provider.utils.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Response {

    private final int code;
    private final String message;
    private final Map<String, String> headers;
    private String body;
    private InputStream stream;

    private Response(int code, String message, Map<String, String> headers) {
        this.code = code;
        this.message = message;
        this.headers = headers;
    }

    public Response(int code, String message, Map<String, String> headers, InputStream stream) {
        this(code, message, headers);
        this.stream = stream;
    }

    public Response(int code, String message, Map<String, String> headers, String body) {
        this(code, message, headers);
        this.body = body;
    }

    private String parseBodyContents() throws IOException {
        if (stream == null) {
            return null;
        }
        if ("gzip".equals(getHeader("Content-Encoding"))) {
            body = StreamUtils.getGzipStreamContents(stream);
        } else {
            body = StreamUtils.getStreamContents(stream);
        }
        return body;
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 400;
    }

    public String getBody() throws IOException {
        return body == null ? parseBodyContents() : body;
    }

    /**
     * 获取有意义的HttpUrlConnection流，可能是inputStream或者errorInputStream
     * @return InputStream
     */
    public InputStream getStream() {
        return stream;
    }

    /**
     * 获取HTTP status code
     *
     * @return the status code
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取HTTP状态信息
     *
     * @return status message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 获取HTTP Headers Map
     *
     * @return headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }


    public String getHeader(String name) {
        return headers.get(name);
    }

    @Override
    public String toString() {
        return "Response{" +
            "code=" + code +
            ", message='" + message + '\'' +
            ", body='" + body + '\'' +
            ", headers=" + headers +
            '}';
    }
}
