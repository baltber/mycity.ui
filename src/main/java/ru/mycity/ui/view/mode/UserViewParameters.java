package ru.mycity.ui.view.mode;

import ru.mycity.ui.service.rest.dto.auth.UserDto;

public class UserViewParameters {
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDto toDto(){
        UserDto userDto = new UserDto();
        userDto.setRole(role);
        return userDto;
    }

}
