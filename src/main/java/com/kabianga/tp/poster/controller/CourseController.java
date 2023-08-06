package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.model.Course;
import com.kabianga.tp.poster.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping("new")
    public ResponseEntity<?> AddCourse(@RequestBody Course course){
        return courseService.addCourse(course);

    }
    //Admin only

    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return courseService.findAll();

    }
    //Admin only

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return courseService.findCardById(id);

    }


    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return courseService.deleteById(id);

    }
}
