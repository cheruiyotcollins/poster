package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.dto.NewSchoolRequest;
import com.kabianga.tp.poster.dto.NewStudentRequest;
import com.kabianga.tp.poster.repository.StudentRepository;
import com.kabianga.tp.poster.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students/")
public class StudentController {
    @Autowired
    StudentService studentService;
    @PostMapping("new")
    public ResponseEntity<?> AddSchool(@RequestBody NewStudentRequest newStudentRequest){
        return studentService.addStudent(newStudentRequest);

    }
    //Admin only

    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return studentService.findAll();

    }
    //Admin only

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return studentService.findCardById(id);

    }


    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return studentService.deleteById(id);

    }
}
