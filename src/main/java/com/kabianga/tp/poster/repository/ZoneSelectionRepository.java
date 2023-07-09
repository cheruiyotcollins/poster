package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.Student;
import com.kabianga.tp.poster.model.ZoneSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ZoneSelectionRepository extends JpaRepository<ZoneSelection,Long> {
    @Query("SELECT z FROM ZoneSelection z where z.student = :student")
    Optional<ZoneSelection> findByStudent(Student student);
}
