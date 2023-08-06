package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.service.SchoolSelectionService;
import com.kabianga.tp.poster.service.ZoneSelectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/selections/schools/")
@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
public class SchoolSelectionController {
    @Autowired
    SchoolSelectionService schoolSelectionService;
    @GetMapping("new/{schoolId}")
    public ResponseEntity<?> makeOrUpdateSelection(Authentication authentication, @PathVariable long schoolId){
                String email= authentication.getName();

        return schoolSelectionService.selectSchool(email,schoolId);
    }
    @GetMapping("list")
    public ResponseEntity<?> findAll(Authentication authentication){
        String email=authentication.getName();
        return schoolSelectionService.findAll();

    }


    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return schoolSelectionService.findSelectionById(id);

    }
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return schoolSelectionService.deleteById(id);

    }

}
