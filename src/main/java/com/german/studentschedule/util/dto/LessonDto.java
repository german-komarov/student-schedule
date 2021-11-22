package com.german.studentschedule.util.dto;

import com.german.studentschedule.domain.Lesson;

import java.time.LocalDate;

public class LessonDto {
    private LocalDate date;
    private String subject;
    private AudienceDto audience;
    private ShortGroupDto group;

    public LessonDto() {
    }

    public LessonDto(LocalDate date, String subject, AudienceDto audience, ShortGroupDto group) {
        this.date = date;
        this.subject = subject;
        this.audience = audience;
        this.group = group;
    }

    public LessonDto(Lesson lesson) {
        if(lesson==null) {
            return;
        }
        this.date = lesson.getDate();
        this.subject = lesson.getSubject().getName();
        this.audience = new AudienceDto(lesson.getAuditory());
        this.group = new ShortGroupDto(lesson.getGroup());
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
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
}
