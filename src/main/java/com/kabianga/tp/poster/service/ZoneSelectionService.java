package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.model.ZoneSelection;
import com.kabianga.tp.poster.repository.ZoneSelectionRepository;
import com.kabianga.tp.poster.repository.StudentRepository;
import com.kabianga.tp.poster.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ZoneSelectionService {
    @Autowired
    ZoneSelectionRepository zoneSelectionRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    ZoneRepository zoneRepository;

    ResponseDto responseDto;

    public ResponseEntity<?> selectZone(String email,long zoneId){
        responseDto=new ResponseDto();
        if(studentRepository.findByEmail(email).isEmpty()){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Student with associated logged in user not found");
            return new  ResponseEntity(responseDto,responseDto.getStatus());

        }
        if(!zoneRepository.existsById(zoneId)){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Zone not found");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }
        if(zoneSelectionRepository.findByStudent(studentRepository.findByEmail(email).get()).isPresent()){
            ZoneSelection selection= zoneSelectionRepository.findByStudent(studentRepository.findByEmail(email).get()).get();
            selection.setZone(zoneRepository.findById(zoneId).get());
            zoneSelectionRepository.save(selection);
            responseDto.setPayload(selection);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Zone updated Successfully");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }
        ZoneSelection selection=new ZoneSelection();
        selection.setZone(zoneRepository.findById(zoneId).get());
        selection.setStudent(studentRepository.findByEmail(email).get());
        zoneSelectionRepository.save(selection);
        responseDto.setPayload(selection);
        responseDto.setStatus(HttpStatus.CREATED);
        responseDto.setDescription("Zone Created Successfully");
        return new  ResponseEntity(responseDto,responseDto.getStatus());



    }
    public ResponseEntity<?> findAll(){
        responseDto=new ResponseDto();
        responseDto.setPayload(zoneSelectionRepository.findAll());
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List of all Zones");
        return new  ResponseEntity(responseDto,responseDto.getStatus());

    }
    public ResponseEntity<?> findSelectionById(long id){
        responseDto=new ResponseDto();
        try{
            if(zoneSelectionRepository.existsById(id)){
                responseDto.setPayload( zoneSelectionRepository.findById(id).get());
                responseDto.setStatus(HttpStatus.FOUND);
                responseDto.setDescription("Success");
                return new ResponseEntity<>(responseDto,responseDto.getStatus());
            }else{
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("No loan with that ID exists");
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

            if(zoneSelectionRepository.existsById(id)){
                zoneSelectionRepository.deleteById(id);
                responseDto.setStatus(HttpStatus.ACCEPTED);
                responseDto.setDescription("Selection deleted successfully");
                return new ResponseEntity<>( responseDto,responseDto.getStatus());
            }else{
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("A Selection with with provided Id is not available");
                return new ResponseEntity<>( responseDto,responseDto.getStatus());
            }



        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Soomething went wrong please try again later");
            return new ResponseEntity<>( responseDto,responseDto.getStatus());
        }
    }

}
