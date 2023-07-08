package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.model.Subject;
import com.kabianga.tp.poster.model.Zone;
import com.kabianga.tp.poster.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zone/")
public class ZoneController {
    @Autowired
    ZoneService zoneService;

    @PostMapping("new")
    public ResponseEntity<?> AddLoanType(@RequestBody Zone zone){
        return zoneService.addZone(zone);

    }
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return zoneService.findAll();

    }
    //Admin only

    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return zoneService.findCardById(id);

    }


    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return zoneService.deleteById(id);

    }
}
