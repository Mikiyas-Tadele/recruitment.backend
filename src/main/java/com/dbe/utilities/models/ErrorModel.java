package com.dbe.utilities.models;

import org.springframework.http.HttpStatus;

/**
 * Created by Mikiyas on 09/08/2017.
 */
public class ErrorModel {
    private HttpStatus status;
    private String message;
    private String url;

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
