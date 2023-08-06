package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.dto.SchoolInfoResponse;
import com.kabianga.tp.poster.dto.SchoolSelectionResponse;
import com.kabianga.tp.poster.model.School;
import com.kabianga.tp.poster.model.SchoolSelection;
import com.kabianga.tp.poster.model.Student;
import com.kabianga.tp.poster.model.Zone;
import com.kabianga.tp.poster.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolSelectionService {
    @Autowired
    ZoneSelectionRepository zoneSelectionRepository;
    @Autowired
    SchoolSelectionRepository schoolSelectionRepository;

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SchoolRepository schoolRepository;

    ResponseDto responseDto;

    public ResponseEntity<?> selectSchool(String email,long schoolId){
        responseDto=new ResponseDto();
        //checking if student exist
        if(studentRepository.findByEmail(email).isEmpty()){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Student with associated logged in user not found");
            return new  ResponseEntity(responseDto,responseDto.getStatus());

        }
        //checking if school exist
        if(!schoolRepository.existsById(schoolId)){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("School not found");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }
        //checking if student has already selected this school
        if(schoolSelectionRepository.findByStudentAndSchoolId(studentRepository.findByEmail(email).get(),schoolRepository.findById(schoolId).get()).isPresent() ){
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("You cant select one school more than once");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }
        //checking if student has selected more than two schools
        if(schoolSelectionRepository.findByStudent(studentRepository.findByEmail(email).get())>1){
            responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("You cant select more than two schools");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }
        //if student has selected a zone then proceed to select school
        //todo only initialize objects once
        if(zoneSelectionRepository.findByStudent(studentRepository.findByEmail(email).get()).isPresent()){
            SchoolSelection schoolSelection= new SchoolSelection();
            schoolSelection.setSchool(schoolRepository.findById(schoolId).get());
            schoolSelection.setStudent(studentRepository.findByEmail(email).get());
            schoolSelectionRepository.save(schoolSelection);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Selection added Successfully");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }
        responseDto.setStatus(HttpStatus.BAD_REQUEST);
        responseDto.setDescription("Something went wrong");
        return new  ResponseEntity(responseDto,responseDto.getStatus());

    }


    public ResponseEntity<?> findAll(){

        responseDto=new ResponseDto();
        List<SchoolSelection> schoolSelections=schoolSelectionRepository.findAll();
        List<SchoolSelectionResponse> schoolSelectionResponses=new ArrayList<>();
        schoolSelections.stream().forEach(schoolSelection -> {
            schoolSelectionResponses.add(setSchoolSelectionResponse(schoolSelection));
        });
        responseDto.setPayload(schoolSelectionResponses);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List of all schools");
        return new  ResponseEntity(responseDto,responseDto.getStatus());

    }
    public ResponseEntity<?> findSelectionById(long id){
        responseDto=new ResponseDto();
        try{
            if(schoolSelectionRepository.existsById(id)){
                //calling setSchoolSelectionResponse to set up SchoolSelectionResponse
                responseDto.setPayload(setSchoolSelectionResponse(schoolSelectionRepository.findById(id).get()));
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
    public SchoolSelectionResponse setSchoolSelectionResponse(SchoolSelection schoolSelection){
        SchoolSelectionResponse schoolSelectionResponse=new SchoolSelectionResponse();
        schoolSelectionResponse.setSchoolName(schoolSelection.getSchool().getName());
        schoolSelectionResponse.setZoneName(zoneSelectionRepository.findByStudent(schoolSelection.getStudent()).get().getZone().getName());
        schoolSelectionResponse.setRegNo(schoolSelection.getStudent().getRegNo());
        schoolSelectionResponse.setStudentName(schoolSelection.getStudent().getName());
        return schoolSelectionResponse;
    }
    public ResponseEntity<?> deleteById(long id){
        responseDto=new ResponseDto();

        try{

            if(schoolSelectionRepository.existsById(id)){
                schoolSelectionRepository.deleteById(id);
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
