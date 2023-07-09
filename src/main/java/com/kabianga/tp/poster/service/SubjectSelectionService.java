package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.dto.SubjectSelectionRequest;
import com.kabianga.tp.poster.model.SubjectSelection;
import com.kabianga.tp.poster.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubjectSelectionService {
    @Autowired
    ZoneSelectionRepository zoneSelectionRepository;
    @Autowired
    SchoolSelectionRepository schoolSelectionRepository;

    @Autowired
    SubjectSelectionRepository subjectSelectionRepository;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SchoolRepository schoolRepository;

    ResponseDto responseDto;

    public ResponseEntity<?> selectSchool(String email, SubjectSelectionRequest subjectSelectionRequest){
        responseDto=new ResponseDto();
        //checking if student exist
        if(studentRepository.findByEmail(email).isEmpty()){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Student with associated logged in user not found");
            return new  ResponseEntity(responseDto,responseDto.getStatus());

        }
        //checking if school selection exist
        if(!schoolSelectionRepository.existsById(subjectSelectionRequest.getSchoolSelectionId())){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("School selection not found");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }

        //checking if there is an existing subject selection in that school
        if(subjectSelectionRepository.findBySchoolSelectionAndSubjectId(subjectSelectionRequest.getSubjectId(),schoolSelectionRepository.findById(subjectSelectionRequest.getSchoolSelectionId()).get()).isPresent()){
                 responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("Subjects combination in selected school has already been selected");
            return new  ResponseEntity(responseDto,responseDto.getStatus());

            //Todo
        }
        //if student has selected a school then proceed to select subjects
           SubjectSelection subjectSelection= new SubjectSelection();
            subjectSelection.setSchoolSelection(schoolSelectionRepository.findById(subjectSelectionRequest.getSchoolSelectionId()).get());
            subjectSelection.setStudent(studentRepository.findByEmail(email).get());
            subjectSelection.setSubjectId(subjectSelectionRequest.getSubjectId());

            subjectSelectionRepository.save(subjectSelection);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Selection added Successfully");
            return new  ResponseEntity(responseDto,responseDto.getStatus());

    }
    public ResponseEntity<?> findAll(){
        responseDto=new ResponseDto();
        responseDto.setPayload(subjectSelectionRepository.findAll());
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List of all Zones");
        return new  ResponseEntity(responseDto,responseDto.getStatus());

    }
    public ResponseEntity<?> findSelectionById(long id){
        responseDto=new ResponseDto();
        try{
            if(subjectSelectionRepository.existsById(id)){
                responseDto.setPayload( subjectSelectionRepository.findById(id).get());
                responseDto.setStatus(HttpStatus.FOUND);
                responseDto.setDescription("Success");
                return new ResponseEntity<>(responseDto,responseDto.getStatus());
            }else{
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("No selection with that ID exists");
                return new ResponseEntity<>( responseDto,HttpStatus.NOT_FOUND);

            }



        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Something went wrong");
            return new ResponseEntity<>( responseDto,responseDto.getStatus());
        }
    }
    public ResponseEntity<?> deleteById(long id){
        responseDto=new ResponseDto();

        try{

            if(subjectSelectionRepository.existsById(id)){
                subjectSelectionRepository.deleteById(id);
                responseDto.setStatus(HttpStatus.ACCEPTED);
                responseDto.setDescription("Selection deleted successfully");
            }else{
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("A Selection with with provided Id is not available");
            }
            return new ResponseEntity<>( responseDto,responseDto.getStatus());


        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Something went wrong please try again later");
            return new ResponseEntity<>( responseDto,responseDto.getStatus());
        }
    }

}
