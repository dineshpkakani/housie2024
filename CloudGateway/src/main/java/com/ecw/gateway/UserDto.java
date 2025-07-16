package com.ecw.gateway;

import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private String fullname;
    private String roles;

}