package com.kabianga.tp.poster.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectSelectionRequest {
    private long schoolSelectionId;
    private long subjectId;

}
