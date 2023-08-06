package com.kabianga.tp.poster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SchoolInfoResponse {
    private  String zoneName;
    private String schoolName;
    private long id;
}
