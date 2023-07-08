package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.model.Course;
import com.kabianga.tp.poster.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    ResponseDto responseDto;

    public ResponseEntity<?> addCourse(Course course){
        responseDto=new ResponseDto();
        try{
            responseDto.setPayload(courseRepository.save(course));
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Course Created Successfully");

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Course not created");

            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findCardById(long id){
        responseDto=new ResponseDto();

        if(courseRepository.existsById(id)){

            return new ResponseEntity<>( courseRepository.findById(id).get(),HttpStatus.FOUND);
        }else{
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Course with provided Id is not available");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);

        }




    }
    public ResponseEntity<?> findAll(){
        responseDto=new ResponseDto();

        try{

            return new ResponseEntity<>( courseRepository.findAll(),HttpStatus.FOUND);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("No Course Found");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteById(long id){
        responseDto=new ResponseDto();

        try{

            if(courseRepository.existsById(id)){
                courseRepository.deleteById(id);
                responseDto.setStatus(HttpStatus.ACCEPTED);
                responseDto.setDescription("Course deleted successfully");
                return new ResponseEntity<>( responseDto,HttpStatus.ACCEPTED);
            }else{
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("A Course with provided Id is not available");
                return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
            }



        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Soemthing went wrong please try again later");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
        }
    }
}
