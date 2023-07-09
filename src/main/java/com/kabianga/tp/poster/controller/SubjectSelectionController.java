package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.dto.SubjectSelectionRequest;
import com.kabianga.tp.poster.service.SchoolSelectionService;
import com.kabianga.tp.poster.service.SubjectSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects/selections/")
public class SubjectSelectionController {
    @Autowired
    SubjectSelectionService subjectSelectionService;
    @PostMapping("new")
    public ResponseEntity<?> makeOrUpdateSelection(@RequestBody SubjectSelectionRequest subjectSelectionRequest){
        String email="kelvincollins86@gmail.com";
        return subjectSelectionService.selectSchool(email,subjectSelectionRequest);
    }
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return subjectSelectionService.findAll();

    }
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return subjectSelectionService.findSelectionById(id);

    }
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return subjectSelectionService.deleteById(id);

    }
}
