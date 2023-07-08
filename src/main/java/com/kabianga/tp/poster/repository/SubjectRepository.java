package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
}
