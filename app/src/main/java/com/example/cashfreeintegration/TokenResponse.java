package com.example.cashfreeintegration;

public class TokenResponse {

    public String status;
    public String message;
    public String cftoken;


    public TokenResponse() {
    }

    public TokenResponse(String status, String message, String cftoken) {
        this.status = status;
        this.message = message;
        this.cftoken = cftoken;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCftoken() {
        return cftoken;
    }

    public void setCftoken(String cftoken) {
        this.cftoken = cftoken;
    }
}
