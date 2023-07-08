package com.kabianga.tp.poster.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kabianga.tp.poster.model.Zone;
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
public class NewSchoolRequest {

    private String name;
    private long zoneId;
}
