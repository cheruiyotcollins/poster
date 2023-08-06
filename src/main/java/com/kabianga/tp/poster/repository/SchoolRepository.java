package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.School;
import com.kabianga.tp.poster.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SchoolRepository extends JpaRepository<School,Long> {
    List<School> findByZone(Zone zone);
}
