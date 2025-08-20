package com.ecw.auth.securityservice.dao;

import lombok.Data;


public record Player (String firstName, String lastName, String email, String phone, String username,String password) {}
