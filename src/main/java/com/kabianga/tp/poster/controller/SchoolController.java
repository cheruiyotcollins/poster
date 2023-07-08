package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.dto.NewSchoolRequest;
import com.kabianga.tp.poster.model.Course;
import com.kabianga.tp.poster.model.School;
import com.kabianga.tp.poster.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schools/")
public class SchoolController {
    @Autowired
    SchoolService schoolService;
    @PostMapping("new")
    public ResponseEntity<?> AddSchool(@RequestBody NewSchoolRequest school){
        return schoolService.addSchool(school);

    }
    //Admin only

    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return schoolService.findAll();

    }
    //Admin only

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return schoolService.findCardById(id);

    }


    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return schoolService.deleteById(id);

    }
}
