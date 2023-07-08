package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.model.Course;
import com.kabianga.tp.poster.model.Subject;
import com.kabianga.tp.poster.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {
    @Autowired
    SubjectRepository subjectRepository;
    ResponseDto responseDto;


    public ResponseEntity<?> addSubject(Subject subject){
        responseDto=new ResponseDto();
        try{
            responseDto.setPayload(subjectRepository.save(subject));
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Subject Created Successfully");

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Subject not added");

            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findCardById(long id){
        responseDto=new ResponseDto();

        if(subjectRepository.existsById(id)){

            return new ResponseEntity<>( subjectRepository.findById(id).get(),HttpStatus.FOUND);
        }else{
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Subject with provided Id is not available");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);

        }




    }
    public ResponseEntity<?> findAll(){
        responseDto=new ResponseDto();

        try{

            return new ResponseEntity<>( subjectRepository.findAll(),HttpStatus.FOUND);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("No Course Found");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteById(long id){
        responseDto=new ResponseDto();

        try{

            if(subjectRepository.existsById(id)){
                subjectRepository.deleteById(id);
                responseDto.setStatus(HttpStatus.ACCEPTED);
                responseDto.setDescription("Subject deleted successfully");
                return new ResponseEntity<>( responseDto,HttpStatus.ACCEPTED);
            }else{
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("A Subject with provided Id is not available");
                return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
            }



        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Something went wrong please try again later");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
        }
    }
}
