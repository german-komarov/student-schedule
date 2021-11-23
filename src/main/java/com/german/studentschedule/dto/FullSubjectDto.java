package com.german.studentschedule.dto;

import com.german.studentschedule.domain.Subject;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FullSubjectDto {
    private Long id;
    private String name;
    private List<ShortGroupDto> groups = new ArrayList<>();
    private List<LessonDto> lessons = new ArrayList<>();
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;


    public FullSubjectDto(Subject subject) {
        if(subject==null) {
            return;
        }
        this.id = subject.getId();
        this.name = subject.getName();
        this.createdAt = subject.getCreatedAt();
        this.updatedAt = subject.getUpdatedAt();

        if(subject.getGroups()!=null) {
            this.groups = subject.getGroups().stream().map(ShortGroupDto::new).collect(Collectors.toList());
        }

        if(subject.getLessons()!=null) {
            this.lessons = subject.getLessons().stream().peek(l->l.setSubject(subject)).map(LessonDto::new).collect(Collectors.toList());
        }
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

    public List<ShortGroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<ShortGroupDto> groups) {
        this.groups = groups;
    }

    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDto> lessons) {
        this.lessons = lessons;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
