package ru.mycity.ui.service.rest.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {

    @JsonProperty("user_name")
    private String userName;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    private String role;

    public UserDto(String userName) {
        this.userName = userName;
    }

    public UserDto() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
