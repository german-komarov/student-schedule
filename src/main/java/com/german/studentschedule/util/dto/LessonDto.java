package com.german.studentschedule.util.dto;

import com.german.studentschedule.domain.Group;
import com.german.studentschedule.domain.Lesson;

import java.time.LocalDate;

public class LessonDto {
    private LocalDate date;
    private String subject;
    private AuditoryDto auditory;
    private ShortGroupDto group;

    public LessonDto() {
    }

    public LessonDto(LocalDate date, String subject, AuditoryDto auditory, ShortGroupDto group) {
        this.date = date;
        this.subject = subject;
        this.auditory = auditory;
        this.group = group;
    }

    public LessonDto(Lesson lesson) {
        if(lesson==null) {
            return;
        }
        this.date = lesson.getDate();
        this.subject = lesson.getSubject().getName();
        this.auditory = new AuditoryDto(lesson.getAuditory());
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

    public AuditoryDto getAuditory() {
        return auditory;
    }

    public void setAuditory(AuditoryDto auditory) {
        this.auditory = auditory;
    }

    public ShortGroupDto getGroup() {
        return group;
    }

    public void setGroup(ShortGroupDto group) {
        this.group = group;
    }
}
