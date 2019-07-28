package ru.mycity.ui.service.rest.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUserRequestDto {

    @JsonProperty("user")
    private UserDto userDto;

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
