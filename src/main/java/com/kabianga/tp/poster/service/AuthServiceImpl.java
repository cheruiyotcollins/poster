package com.kabianga.tp.poster.service;


import com.kabianga.tp.poster.dto.AddRoleRequest;
import com.kabianga.tp.poster.dto.LoginDto;
import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.dto.SignUpRequest;
import com.kabianga.tp.poster.model.Role;
import com.kabianga.tp.poster.model.User;
import com.kabianga.tp.poster.repository.RoleRepository;
import com.kabianga.tp.poster.repository.UserRepository;
import com.kabianga.tp.poster.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    ResponseDto responseDto =new ResponseDto();


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);


        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public ResponseEntity<?> register(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity("Email Address already in use!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity( "Username is already taken!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        //todo since registering using this method is for only students, check if there is corresponding email in student table

        // Creating user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setMsisdn(signUpRequest.getMsisdn());
           if(roleRepository.findByName("STUDENT").isPresent()){
                Role userRole = roleRepository.findByName("STUDENT").get();
                 user.setRoles(Collections.singleton(userRole));
            }
         userRepository.save(user);

        return new ResponseEntity("User registered successfully",HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<?> addRole(AddRoleRequest addRoleRequest) {
        Role role=new Role();
        role.setName(addRoleRequest.getName());
        roleRepository.save(role);
        return null;
    }
    @Override
    public ResponseEntity<?> findUserById(long id){

        try{

            return new ResponseEntity<>( userRepository.findById(id).get(),HttpStatus.OK);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("User Not Found");
            return new ResponseEntity<>(responseDto,HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<?> findAll(){

        try{

            return new ResponseEntity<>( userRepository.findAll(),HttpStatus.OK);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("No User Found");
            return new ResponseEntity<>(responseDto,HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<?> deleteById(long id){

        try{
            responseDto.setStatus(HttpStatus.ACCEPTED);
            responseDto.setDescription("User deleted successfully");
            userRepository.deleteById(id);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("User with that id not found");
            return new ResponseEntity<>(responseDto,HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> addUser(SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity("Email Address already in use!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity( "Username is already taken!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println(":::::::::::::::::::::::::::"+signUpRequest.getPassword());

        // Creating user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setName(signUpRequest.getName());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setMsisdn(signUpRequest.getMsisdn());
        if(roleRepository.existsById(signUpRequest.getRoleId())){
            Role userRole = roleRepository.findById(signUpRequest.getRoleId()).get();
            user.setRoles(Collections.singleton(userRole));
        }
        userRepository.save(user);

        return new ResponseEntity("User Added successfully",HttpStatus.ACCEPTED);
    }


}
