package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByEmail(String email);
}
