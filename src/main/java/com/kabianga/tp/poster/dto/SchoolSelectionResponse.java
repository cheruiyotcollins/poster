package com.kabianga.tp.poster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolSelectionResponse {
    private String regNo;
    private String studentName;
    private  String zoneName;
    private String schoolName;
}
