package com.kabianga.tp.poster.controller;

import com.kabianga.tp.poster.service.ZoneSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/selections/zones/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ZoneSelectionController {
    @Autowired
    ZoneSelectionService zoneSelectionService;
    @GetMapping("new/{zoneId}")
    public ResponseEntity<?> makeOrUpdateSelection(Authentication authentication, @PathVariable long zoneId){
        String email =authentication.getName();
          return zoneSelectionService.selectZone(email,zoneId);
    }
    @GetMapping("list")
    public ResponseEntity<?> findAll(){
        return zoneSelectionService.findAll();

    }
    @GetMapping("find/{id}")
    public ResponseEntity<?> findById(@PathVariable long id){
        return zoneSelectionService.findZoneSelectionById(id);

    }
    @GetMapping("delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return zoneSelectionService.deleteById(id);

    }

}
