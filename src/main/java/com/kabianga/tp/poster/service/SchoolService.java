package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.AddSchoolRequest;
import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.model.School;
import com.kabianga.tp.poster.model.Zone;
import com.kabianga.tp.poster.repository.SchoolRepository;
import com.kabianga.tp.poster.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SchoolService {
    @Autowired
    SchoolRepository schoolRepository;

    @Autowired
    ZoneRepository zoneRepository;
    ResponseDto responseDto;

    public ResponseEntity<?> addSchool(AddSchoolRequest addSchoolRequest){
        responseDto=new ResponseDto();
        try{
            School school=new School();
            if(!zoneRepository.existsById(addSchoolRequest.getZoneId())){
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("Zone Not Found");
                return new ResponseEntity<>(responseDto, responseDto.getStatus());
            }
            Zone zone=zoneRepository.findById(addSchoolRequest.getZoneId()).get();
            school.setName(addSchoolRequest.getName());
            school.setZone(zone);
            responseDto.setPayload(schoolRepository.save(school));
             responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("School Added Successfully");

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("School not created");

            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findCardById(long id){
        responseDto=new ResponseDto();

        if(schoolRepository.existsById(id)){

            return new ResponseEntity<>( schoolRepository.findById(id).get(),HttpStatus.FOUND);
        }else{
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("School with provided Id is not available");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);

        }




    }
    public ResponseEntity<?> findAll(){
        responseDto=new ResponseDto();

        try{

            return new ResponseEntity<>( schoolRepository.findAll(),HttpStatus.FOUND);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("No School Found");
            return new ResponseEntity<>( responseDto,responseDto.getStatus());
        }
    }

    public ResponseEntity<?> deleteById(long id){
        responseDto=new ResponseDto();

        try{

            if(schoolRepository.existsById(id)){
                schoolRepository.deleteById(id);
                responseDto.setStatus(HttpStatus.ACCEPTED);
                responseDto.setDescription("School deleted successfully");
                return new ResponseEntity<>( responseDto,HttpStatus.ACCEPTED);
            }else{
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("A School with provided Id is not available");
                return new ResponseEntity<>( responseDto,responseDto.getStatus());
            }



        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Something went wrong please try again later");
            return new ResponseEntity<>( responseDto,responseDto.getStatus());
        }
    }
}
