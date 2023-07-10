package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.dto.SchoolSelectionResponse;
import com.kabianga.tp.poster.dto.SubjectSelectionRequest;
import com.kabianga.tp.poster.dto.SubjectSelectionResponse;
import com.kabianga.tp.poster.model.SchoolSelection;
import com.kabianga.tp.poster.model.SubjectSelection;
import com.kabianga.tp.poster.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    SubjectRepository subjectRepository;
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
        //Todo student cannot select more than one combination in a school
        //TODO if a lecturer has confirmed one school for the student then the other selected should be set to DECLINED

        //checking if there is an existing subject selection in that school
        if(subjectSelectionRepository.findBySchoolSelectionAndSubjectId(subjectRepository.findById(subjectSelectionRequest.getSubjectId()).get()
                ,schoolSelectionRepository.findById(subjectSelectionRequest.getSchoolSelectionId()).get()).isPresent()
//               &&subjectSelectionRepository.findByStatus("PENDING").isPresent()
        ){
                 responseDto.setStatus(HttpStatus.NOT_ACCEPTABLE);
            responseDto.setDescription("Subjects combination in selected school has already been selected");
            return new  ResponseEntity(responseDto,responseDto.getStatus());


        }
        //if student has selected a school then proceed to select subjects
            SubjectSelection subjectSelection= new SubjectSelection();
            subjectSelection.setSchoolSelection(schoolSelectionRepository.findById(subjectSelectionRequest.getSchoolSelectionId()).get());
            subjectSelection.setStudent(studentRepository.findByEmail(email).get());
            subjectSelection.setSubject(subjectRepository.findById(subjectSelectionRequest.getSubjectId()).get());
            subjectSelection.setStatus("PENDING");

            subjectSelectionRepository.save(subjectSelection);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Selection added Successfully");
            return new  ResponseEntity(responseDto,responseDto.getStatus());

    }
    public ResponseEntity<?> findAll(){
        responseDto=new ResponseDto();
        List<SubjectSelection> subjectSelectionList=subjectSelectionRepository.findAll();
        List<SubjectSelectionResponse> subjectSelectionResponseList=new ArrayList<>();
        subjectSelectionList.stream().forEach(subjectSelection -> {
            subjectSelectionResponseList.add(setSchoolSelectionResponse(subjectSelection));
        });
        responseDto.setPayload(subjectSelectionResponseList);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List of all Zones");
        return new  ResponseEntity(responseDto,responseDto.getStatus());

    }
    public ResponseEntity<?> findSelectionById(long id){
        responseDto=new ResponseDto();
        try{
            if(subjectSelectionRepository.existsById(id)){
                responseDto.setPayload(setSchoolSelectionResponse( subjectSelectionRepository.findById(id).get()));
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
    public SubjectSelectionResponse setSchoolSelectionResponse(SubjectSelection subjectSelection){
        SubjectSelectionResponse subjectSelectionResponse=new SubjectSelectionResponse();
        subjectSelectionResponse.setSchoolName(subjectSelection.getSchoolSelection().getSchool().getName());
        subjectSelectionResponse.setZoneName(zoneSelectionRepository.findByStudent(subjectSelection.getStudent()).get().getZone().getName());
        subjectSelectionResponse.setRegNo(subjectSelection.getStudent().getRegNo());
        subjectSelectionResponse.setStudentName(subjectSelection.getStudent().getName());
        subjectSelectionResponse.setSubjectCombination(subjectSelection.getSubject().getName());
        return subjectSelectionResponse;
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
