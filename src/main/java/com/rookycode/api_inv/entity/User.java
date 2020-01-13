package com.rookycode.api_inv.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;

@Builder(builderClassName = "UserBuilder", toBuilder = true)
@JsonDeserialize(builder = User.UserBuilder.class)
@Data
public class User {
    private String name;
    private String password;
    private String email;
    private String enabled;
}