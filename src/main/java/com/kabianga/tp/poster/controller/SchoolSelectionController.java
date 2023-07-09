package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.service.SchoolSelectionService;
import com.kabianga.tp.poster.service.ZoneSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schools/selections/")
public class SchoolSelectionController {
    @Autowired
    SchoolSelectionService schoolSelectionService;
    @GetMapping("new/{schoolId}")
    public ResponseEntity<?> makeOrUpdateSelection(@PathVariable long schoolId){
        String email="kelvincollins86@gmail.com";
        //todo Authentication authentication
        return schoolSelectionService.selectSchool(email,schoolId);
    }
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
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
