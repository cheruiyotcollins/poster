package com.kabianga.tp.poster.repository;

import com.kabianga.tp.poster.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
