package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.model.Zone;
import com.kabianga.tp.poster.service.ZoneSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/zones/selections/")
public class ZoneSelectionController {
    @Autowired
    ZoneSelectionService zoneSelectionService;
    @GetMapping("new/{zoneId}")
    public ResponseEntity<?> makeOrUpdateSelection(@PathVariable long zoneId){
        String email="kelvincollins86@gmail.com";
        return zoneSelectionService.selectZone(email,zoneId);
    }
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return zoneSelectionService.findAll();

    }
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return zoneSelectionService.findSelectionById(id);

    }
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return zoneSelectionService.deleteById(id);

    }

}
