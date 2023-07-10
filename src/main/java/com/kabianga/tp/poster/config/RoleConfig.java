package com.kabianga.tp.poster.config;


import com.kabianga.tp.poster.model.Role;
import com.kabianga.tp.poster.repository.RoleRepository;
import com.kabianga.tp.poster.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RoleConfig implements CommandLineRunner {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        log.info(":::::::::::checking if roles exist, if it doesn't add them::::::::::::");
       String Admin="ADMIN";
       String Student="STUDENT";
       String Lecturer="LECTURER";
       if(!roleRepository.findByName(Admin).isPresent()){
           Role role=new Role();
           role.setName(Admin);
           roleRepository.save(role);
       }if(!roleRepository.findByName(Student).isPresent()){
            Role role=new Role();
            role.setName(Student);
            roleRepository.save(role);
        }
        if(!roleRepository.findByName(Lecturer).isPresent()){
            Role role=new Role();
            role.setName(Lecturer);
            roleRepository.save(role);
        }

    }
}