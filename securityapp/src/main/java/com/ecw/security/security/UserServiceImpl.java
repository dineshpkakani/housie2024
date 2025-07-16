package com.ecw.security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

 @Autowired
 PasswordEncoder passwordEncoder;

 private UserRepository userRepository;

 public UserServiceImpl(UserRepository userRepository) {
  super();
  this.userRepository = userRepository;
 }

 @Override
 public User findByUsername(String username) {
  return userRepository.findByUsername(username);
 }

 @Override
 public User save(UserDto userDto) {
  User user = User.builder()
          .password(userDto.getPassword())
          .username(userDto.getUsername())
          .roles(userDto.getRoles())
          .build();

 /* User user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()),
          userDto.getFullname(),userDto.getRoles());
*/
  return userRepository.save(user);
 }

}