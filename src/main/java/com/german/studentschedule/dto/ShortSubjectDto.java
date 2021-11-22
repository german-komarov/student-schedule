package com.german.studentschedule.dto;

import com.german.studentschedule.domain.Subject;

public class ShortSubjectDto {
    private Long id;
    private String name;

    public ShortSubjectDto() {
    }

    public ShortSubjectDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ShortSubjectDto(Subject subject) {
        if(subject==null) {
            return;
        }
        this.id = subject.getId();
        this.name = subject.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
