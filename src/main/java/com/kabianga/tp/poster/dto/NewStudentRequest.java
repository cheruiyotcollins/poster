package com.kabianga.tp.poster.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kabianga.tp.poster.model.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewStudentRequest {

    private String name;
    private String regNo;

    private long courseId;
}
