package com.german.studentschedule.dto;

import com.german.studentschedule.domain.Group;
import com.german.studentschedule.domain.Subject;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FullSubjectDto {
    private Long id;
    private String name;
    private Set<ShortGroupDto> groups;
    private Set<LessonDto> lessons;
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
            this.groups = subject.getGroups().stream().map(ShortGroupDto::new).collect(Collectors.toSet());
        }
        if(subject.getLessons()!=null) {
            this.lessons = subject.getLessons().stream().peek(l->l.setSubject(subject)).map(LessonDto::new).collect(Collectors.toSet());
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

    public Set<ShortGroupDto> getGroups() {
        return groups;
    }

    public void setGroups(Set<ShortGroupDto> groups) {
        this.groups = groups;
    }

    public Set<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonDto> lessons) {
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
