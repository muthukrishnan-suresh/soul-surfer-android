package com.soulsurfer.android.model.network;

public class Response {
    private String data;
    private ResponseCode code;

    public Response(String data, ResponseCode code) {
        this.data = data;
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResponseCode getCode() {
        return code;
    }

    public void setCode(ResponseCode code) {
        this.code = code;
    }
}
