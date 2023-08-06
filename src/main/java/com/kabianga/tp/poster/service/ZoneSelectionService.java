package com.kabianga.tp.poster.service;

import com.kabianga.tp.poster.dto.ResponseDto;
import com.kabianga.tp.poster.dto.ZoneSelectionResponse;
import com.kabianga.tp.poster.model.ZoneSelection;
import com.kabianga.tp.poster.repository.ZoneSelectionRepository;
import com.kabianga.tp.poster.repository.StudentRepository;
import com.kabianga.tp.poster.repository.ZoneRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
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
        //checking if there is an associated student with logged in user email
        if(studentRepository.findByEmail(email).isEmpty()){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            log.info("student not found");
            responseDto.setDescription("Student with associated logged in user not found");
            return new  ResponseEntity(responseDto,responseDto.getStatus());

        }
        //check if zone exist
        if(!zoneRepository.existsById(zoneId)){
            responseDto.setStatus(HttpStatus.NOT_FOUND);
            log.info("Zone not found");
            responseDto.setDescription("Zone not found");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }
        //checking if student already picked a zone, if yes update it
        //todo if student already picked schools he/she cant update zone
        if(zoneSelectionRepository.findByStudent(studentRepository.findByEmail(email).get()).isPresent()){
            ZoneSelection selection= zoneSelectionRepository.findByStudent(studentRepository.findByEmail(email).get()).get();
            selection.setZone(zoneRepository.findById(zoneId).get());
            zoneSelectionRepository.save(selection);
            responseDto.setPayload(selection);
            responseDto.setStatus(HttpStatus.CREATED);
            responseDto.setDescription("Zone selection updated Successfully");
            return new  ResponseEntity(responseDto,responseDto.getStatus());
        }

        ZoneSelection selection=new ZoneSelection();
        selection.setZone(zoneRepository.findById(zoneId).get());
        selection.setStudent(studentRepository.findByEmail(email).get());
        zoneSelectionRepository.save(selection);
        responseDto.setPayload(selection);
        responseDto.setStatus(HttpStatus.CREATED);
        responseDto.setDescription("Zone selected Successfully");
        return new  ResponseEntity(responseDto,responseDto.getStatus());



    }
    public ResponseEntity<?> findAll(){
        responseDto=new ResponseDto();
        List<ZoneSelection> zoneSelectionList=zoneSelectionRepository.findAll();
        List<ZoneSelectionResponse> zoneSelectionResponseList=new ArrayList<>();
        zoneSelectionList.stream().forEach(zoneSelection -> {
            zoneSelectionResponseList.add(setZoneSelectionResponse(zoneSelection));
        });
        responseDto.setPayload(zoneSelectionResponseList);
        responseDto.setStatus(HttpStatus.FOUND);
        responseDto.setDescription("List of all Zones");
        return new  ResponseEntity(responseDto,responseDto.getStatus());

    }
    public ResponseEntity<?> findZoneSelectionById(long id){
        responseDto=new ResponseDto();
        try{
            if(zoneSelectionRepository.existsById(id)){
                ZoneSelection zoneSelection=zoneSelectionRepository.findById(id).get();

                responseDto.setPayload( setZoneSelectionResponse(zoneSelection));
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
    public ZoneSelectionResponse setZoneSelectionResponse(ZoneSelection zoneSelection){
        ZoneSelectionResponse zoneSelectionResponse=new ZoneSelectionResponse();
        zoneSelectionResponse.setZoneName(zoneSelection.getZone().getName());
        zoneSelectionResponse.setRegNo(zoneSelection.getStudent().getRegNo());
        zoneSelectionResponse.setStudentName(zoneSelection.getStudent().getName());
        return zoneSelectionResponse;

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
