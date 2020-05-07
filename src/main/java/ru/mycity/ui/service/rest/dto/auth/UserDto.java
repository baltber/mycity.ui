package ru.mycity.ui.service.rest.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserDto {


    @JsonProperty("name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("role")
    private String role;
    @JsonProperty("organisation_guid")
    private String organisationGuid;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getOrganisationGuid() {
        return organisationGuid;
    }

    public void setOrganisationGuid(String organisationGuid) {
        this.organisationGuid = organisationGuid;
    }
}
