package com.soulsurfer.android.model.network;

public enum ResponseCode {
    OK(200);

    private int code;

    ResponseCode(int code) {
        this.code = code;
    }

    public static ResponseCode get(int responseCodeInt) {
        for (ResponseCode responseCode: ResponseCode.values()) {
            if (responseCode.code == responseCodeInt) {
                return responseCode;
            }
        }
        return null;
    }
}
