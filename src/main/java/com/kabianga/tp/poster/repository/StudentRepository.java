package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
