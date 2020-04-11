package com.soulsurfer.android.model.network;

import com.soulsurfer.android.utils.Constants;

import java.io.IOException;
import java.net.HttpURLConnection;

class Request {

    private String url;
    private String method;
    private int connectionTimeout = Constants.CONNECTION_TIMEOUT;
    private int readTimeout = Constants.READ_TIMEOUT;

    private Request(String url, String method) {
        this.url = url;
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public static RequestBuilder builder(String url, HttpMethod method) {
        return new RequestBuilder(url, method);
    }

    static final class RequestBuilder {
        private String url;
        private HttpMethod method;
        private int connectionTimeout = Constants.CONNECTION_TIMEOUT;
        private int readTimeout = Constants.READ_TIMEOUT;

        private RequestBuilder(String url, HttpMethod method) {
            if (url == null || url.trim().length() == 0) {
                throw new IllegalArgumentException("url should not be empty.");
            }

            if (method == null) {
                throw new IllegalArgumentException("method should not be null.");
            }

            this.url = url;
            this.method = method;
        }

        public RequestBuilder setConnectionTimeout(int connectionTimeout) {

            if (connectionTimeout <= 0) {
                throw new IllegalArgumentException("connectionTimeout should be greater than 0.");
            }

            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public RequestBuilder setReadTimeout(int readTimeout) {

            if (readTimeout <= 0) {
                throw new IllegalArgumentException("readTimeout should be greater than 0.");
            }

            this.readTimeout = readTimeout;
            return this;
        }

        public Response response() throws IOException {
            Request request = new Request(url, method.asString());
            request.connectionTimeout = connectionTimeout;
            request.readTimeout = readTimeout;
            HttpURLConnection connection = RequestConnectionHelper.getConnection(request);
            return RequestConnectionHelper.getResponse(connection);
        }
    }
}
