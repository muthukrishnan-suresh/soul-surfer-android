package com.soulsurfer.android.model.network;

import android.util.Log;

import com.soulsurfer.android.utils.Constants;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

class RequestConnectionHelper {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;
    private static final int EOF = -1;

    static HttpURLConnection getConnection(Request request) throws IOException {
        URL httpURL = new URL(request.getUrl());
        HttpURLConnection connection = (HttpURLConnection) httpURL.openConnection();
        connection.setRequestMethod(request.getMethod());
        connection.setConnectTimeout(request.getConnectionTimeout());
        connection.setConnectTimeout(request.getReadTimeout());
        connection.connect();
        return connection;
    }

    static String streamToString(InputStream is, Charset encoding) throws IOException {
        StringBuilder stringOut = new StringBuilder();
        try {
            char[] buffer = new char[DEFAULT_BUFFER_SIZE];
            if (is != null) {
                InputStreamReader isReader = new InputStreamReader(is, encoding);
                int readBytesLen = 0;

                while (EOF != (readBytesLen = isReader.read(buffer))) {
                    stringOut.append(buffer, 0, readBytesLen);
                }
            }
        } finally {
            close(is);
        }
        return stringOut.toString();
    }

    static void close(Closeable... closeables) {
        if(closeables == null){
            return;
        }
        for(Closeable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (Exception e) {
                Log.e(Constants.TAG,e.toString());
            }
        }
    }

    public static Response getResponse(HttpURLConnection connection) throws IOException {
        Charset charset = Charset.forName("UTF-8");
        String data = RequestConnectionHelper.streamToString(connection.getInputStream(), charset);
        ResponseCode code = ResponseCode.get(connection.getResponseCode());
        return new Response(data, code);
    }
}
