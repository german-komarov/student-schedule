package com.german.studentschedule.dto;

import com.german.studentschedule.domain.Lesson;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class LessonDto {
    private Long id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private LocalDate date;
    private ShortSubjectDto subject;
    private AudienceDto audience;
    private ShortGroupDto group;

    public LessonDto() {
    }

    public LessonDto(Lesson lesson) {
        if(lesson==null) {
            return;
        }
        this.id = lesson.getId();
        this.createdAt = lesson.getCreatedAt();
        this.updatedAt = lesson.getUpdatedAt();
        this.date = lesson.getDate();
        this.subject = new ShortSubjectDto(lesson.getSubject());
        this.audience = new AudienceDto(lesson.getAudience());
        this.group = new ShortGroupDto(lesson.getGroup());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ShortSubjectDto getSubject() {
        return subject;
    }

    public void setSubject(ShortSubjectDto subject) {
        this.subject = subject;
    }

    public AudienceDto getAudience() {
        return audience;
    }

    public void setAudience(AudienceDto audience) {
        this.audience = audience;
    }

    public ShortGroupDto getGroup() {
        return group;
    }

    public void setGroup(ShortGroupDto group) {
        this.group = group;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
