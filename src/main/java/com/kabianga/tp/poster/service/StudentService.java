package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.AddStudentRequest;
import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.model.Course;
import com.kabianga.tp.poster.model.Student;
import com.kabianga.tp.poster.repository.CourseRepository;
import com.kabianga.tp.poster.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;


@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    ResponseDto responseDto;

    public ResponseEntity<?> addStudent(AddStudentRequest addStudentRequest) {
        responseDto = new ResponseDto();
        try {
            Student student = new Student();
            if (!courseRepository.existsById(addStudentRequest.getCourseId())) {
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("Course Not Found");
                return new ResponseEntity<>(responseDto, responseDto.getStatus());
            }
            Course course = courseRepository.findById(addStudentRequest.getCourseId()).get();
            student.setName(addStudentRequest.getName());
            student.setCourse(course);
            student.setRegNo(addStudentRequest.getRegNo());
            student.setEmail(addStudentRequest.getEmail());
            responseDto.setPayload(studentRepository.save(student));
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Student Added Successfully");

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Student not created");

            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findCardById(long id) {
        responseDto = new ResponseDto();

        if (studentRepository.existsById(id)) {

            return new ResponseEntity<>(studentRepository.findById(id).get(), HttpStatus.FOUND);
        } else {
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Student with provided Id is not available");
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);

        }


    }

    public ResponseEntity<?> findAll() {
        responseDto = new ResponseDto();

        try {

            return new ResponseEntity<>(studentRepository.findAll(), HttpStatus.FOUND);

        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("No Student Found");
            return new ResponseEntity<>(responseDto, responseDto.getStatus());
        }
    }

    public ResponseEntity<?> deleteById(long id) {
        responseDto = new ResponseDto();

        try {

            if (studentRepository.existsById(id)) {
                studentRepository.deleteById(id);
                responseDto.setStatus(HttpStatus.ACCEPTED);
                responseDto.setDescription("Student deleted successfully");
                return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
            } else {
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("A Student with provided Id is not available");
                return new ResponseEntity<>(responseDto, responseDto.getStatus());
            }


        } catch (Exception e) {
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Something went wrong please try again later");
            return new ResponseEntity<>(responseDto, responseDto.getStatus());
        }
    }
}