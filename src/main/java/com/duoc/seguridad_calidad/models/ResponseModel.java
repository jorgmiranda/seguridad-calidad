package com.duoc.seguridad_calidad.models;

public class ResponseModel {
    private String messageResponse;
    private Object data;
    private String error;

    public ResponseModel() {}

    public ResponseModel(String messageResponse, Object data, String error) {
        this.messageResponse = messageResponse;
        this.data = data;
        this.error = error;
    }

    public String getMessageResponse() {
        return messageResponse;
    }

    public void setMessageResponse(String messageResponse) {
        this.messageResponse = messageResponse;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
