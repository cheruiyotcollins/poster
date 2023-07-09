package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.SchoolSelection;

import com.kabianga.tp.poster.model.SubjectSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubjectSelectionRepository extends JpaRepository<SubjectSelection,Long> {
    @Query("SELECT s FROM SubjectSelection s where s.schoolSelection=:schoolSelection and s.subjectId = :subjectId")
    Optional<SubjectSelection> findBySchoolSelectionAndSubjectId(long subjectId, SchoolSelection schoolSelection);
}
