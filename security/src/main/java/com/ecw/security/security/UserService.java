package com.ecw.security.security;


public interface UserService {
 User findByUsername(String username);

 User save(UserDto userDto);

}