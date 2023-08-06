package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.dto.AddStudentRequest;
import com.kabianga.tp.poster.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {
    @Autowired
    StudentService studentService;
    @PostMapping("new")
    public ResponseEntity<?> AddSchool(@RequestBody AddStudentRequest addStudentRequest){
        return studentService.addStudent(addStudentRequest);

    }


    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return studentService.findAll();

    }
    //Admin only

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return studentService.findCardById(id);

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','LECTURER')")
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return studentService.deleteById(id);

    }
}
