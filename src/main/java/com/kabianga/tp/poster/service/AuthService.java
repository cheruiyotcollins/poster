package com.kabianga.tp.poster.service;


import com.kabianga.tp.poster.dto.AddRoleRequest;
import com.kabianga.tp.poster.dto.LoginDto;
import com.kabianga.tp.poster.dto.SignUpRequest;
import com.kabianga.tp.poster.model.User;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    String login(LoginDto loginDto);

    ResponseEntity<?> register(SignUpRequest signUpRequest);
    ResponseEntity<?> addRole(AddRoleRequest addRoleRequest);

    ResponseEntity<?> findUserById(long id);

    ResponseEntity<?> findAll();

    ResponseEntity<?> deleteById(long id);


    ResponseEntity<?> addUser(SignUpRequest signUpRequest);

    ResponseEntity<?> getCurrentUser(String name);
}
