package com.ecw.gateway;


public interface UserService {
    User findByUsername(String username);

    User save(UserDto userDto);

}