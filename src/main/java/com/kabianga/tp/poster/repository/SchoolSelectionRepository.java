package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.School;
import com.kabianga.tp.poster.model.SchoolSelection;
import com.kabianga.tp.poster.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SchoolSelectionRepository extends JpaRepository<SchoolSelection,Long> {
    @Query("SELECT count(*) FROM SchoolSelection s where s.student = :student")
    int findByStudent(Student student);
    @Query("SELECT s FROM SchoolSelection s where s.student = :student and s.school=:school")
   Optional<SchoolSelection> findByStudentAndSchoolId(Student student, School school);
}
