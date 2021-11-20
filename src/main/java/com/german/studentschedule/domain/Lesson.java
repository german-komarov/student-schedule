package com.german.studentschedule.domain;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "lessons")
public class Lesson extends BaseModel {

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Subject subject;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Auditory auditory;

    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Auditory getAuditory() {
        return auditory;
    }

    public void setAuditory(Auditory auditory) {
        this.auditory = auditory;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
