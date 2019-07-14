package ru.mycity.ui.service.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultDto {
    @JsonProperty(value = "status_code")
    private String statusCode;
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
