package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.model.Course;
import com.kabianga.tp.poster.model.Zone;
import com.kabianga.tp.poster.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ZoneService {
    @Autowired
    ZoneRepository zoneRepository;
    ResponseDto responseDto;

    public ResponseEntity<?> addZone(Zone zone){
        responseDto=new ResponseDto();
        try{
            responseDto.setPayload(zoneRepository.save(zone));
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Zone Added Successfully");

            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Zone not created");

            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> findCardById(long id){
        responseDto=new ResponseDto();

        if(zoneRepository.existsById(id)){

            return new ResponseEntity<>( zoneRepository.findById(id).get(),HttpStatus.FOUND);
        }else{
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            responseDto.setDescription("Zone with provided Id is not available");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);

        }




    }
    public ResponseEntity<?> findAll(){
        responseDto=new ResponseDto();

        try{

            return new ResponseEntity<>( zoneRepository.findAll(),HttpStatus.OK);

        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("No Zone Found");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> deleteById(long id){
        responseDto=new ResponseDto();

        try{

            if(zoneRepository.existsById(id)){
                zoneRepository.deleteById(id);
                responseDto.setStatus(HttpStatus.ACCEPTED);
                responseDto.setDescription("Zone deleted successfully");
                return new ResponseEntity<>( responseDto,HttpStatus.ACCEPTED);
            }else{
                responseDto.setStatus(HttpStatus.NOT_FOUND);
                responseDto.setDescription("A Zone with provided Id is not available");
                return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
            }



        }catch(Exception e){
            responseDto.setStatus(HttpStatus.BAD_REQUEST);
            responseDto.setDescription("Something went wrong please try again later");
            return new ResponseEntity<>( responseDto,HttpStatus.BAD_REQUEST);
        }
    }
}
