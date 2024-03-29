package com.german.studentschedule.dto;

import com.german.studentschedule.domain.Group;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FullGroupDto {
    private Long id;
    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private List<ShortSubjectDto> subjects = new ArrayList<>();
    private List<ShortUserDto> students = new ArrayList<>();
    private List<LessonDto> lessons = new ArrayList<>();

    public FullGroupDto() {
    }

    public FullGroupDto(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.createdAt = group.getCreatedAt();
        this.updatedAt = group.getUpdatedAt();

        if(group.getSubjects()!=null) {
            this.subjects = group.getSubjects().stream().map(ShortSubjectDto::new).collect(Collectors.toList());
        }

        if(group.getStudents()!=null) {
            this.students = group.getStudents().stream().map(ShortUserDto::new).collect(Collectors.toList());
        }

        if(group.getLessons()!=null) {
            this.lessons = group.getLessons().stream().map(LessonDto::new).collect(Collectors.toList());
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

    public List<ShortSubjectDto> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<ShortSubjectDto> subjects) {
        this.subjects = subjects;
    }

    public List<ShortUserDto> getStudents() {
        return students;
    }

    public void setStudents(List<ShortUserDto> students) {
        this.students = students;
    }

    public List<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDto> lessons) {
        this.lessons = lessons;
    }
}
