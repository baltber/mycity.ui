package ru.mycity.ui.service.rest.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthUserRequestDto {

    @JsonProperty("email")
    private String userName;
    @JsonProperty("password")
    private String password;

    public AuthUserRequestDto(String userName) {
        this.userName = userName;
    }

    public AuthUserRequestDto() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
