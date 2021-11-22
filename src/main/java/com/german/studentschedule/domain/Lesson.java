package com.german.studentschedule.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "lessons")
public class Lesson extends BaseModel {

    @Column(nullable = false)
    private LocalDate date;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Subject subject;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    private Audience audience;

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

    public Audience getAuditory() {
        return audience;
    }

    public void setAuditory(Audience audience) {
        this.audience = audience;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

}
