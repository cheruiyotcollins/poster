package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.dto.AddSchoolRequest;
import com.kabianga.tp.poster.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SchoolController {
    @Autowired
    SchoolService schoolService;
    @PostMapping("new")
    public ResponseEntity<?> AddSchool(@RequestBody AddSchoolRequest school){
        return schoolService.addSchool(school);

    }
    //Admin only

    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return schoolService.findAll();

    }
    @GetMapping("zone/list")
    public ResponseEntity<?> findSchoolsPerZone(Authentication authentication){
        String email=authentication.getName();
        return schoolService.findSchoolsPerZone(email);

    }
    //Admin only

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return schoolService.findCardById(id);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','LECTURER')")
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return schoolService.deleteById(id);

    }
}
