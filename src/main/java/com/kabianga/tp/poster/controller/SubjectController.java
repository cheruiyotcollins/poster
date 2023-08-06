package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.model.Course;
import com.kabianga.tp.poster.model.Subject;
import com.kabianga.tp.poster.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subjects/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SubjectController {
    @Autowired
    SubjectService subjectService;

    @PostMapping("new")
    public ResponseEntity<?> AddLoanType(@RequestBody Subject subject){
        return subjectService.addSubject(subject);

    }
      @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return subjectService.findAll();

    }
    //Admin only

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return subjectService.findCardById(id);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','LECTURER')")
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return subjectService.deleteById(id);

    }
}
