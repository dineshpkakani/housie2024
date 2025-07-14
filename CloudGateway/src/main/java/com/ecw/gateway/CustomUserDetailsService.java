package com.ecw.security.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

 private UserRepository userRepository;

 @Autowired
 private PasswordEncoder bCryptPasswordEncoder;

 public CustomUserDetailsService(UserRepository userRepository) {
  super();
  this.userRepository = userRepository;
 }


 @Override
 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

  User user = userRepository.findByUsername(username);
  if (user == null) {
   throw new UsernameNotFoundException("Username or Password not found");
  }
  String encriptedPasswd=bCryptPasswordEncoder.encode(user.getPassword());

  return new CustomUserDetails(user.getUsername(),user.getPassword(), authorities(user.getRoles()), user.getName());
 }

 public Collection<? extends GrantedAuthority> authorities(String role) {
  return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+role));
 }

}