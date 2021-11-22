package com.german.studentschedule.domain;


import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "subjects")
public class Subject extends BaseModel {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "subject")
    private Set<Lesson> lessons;

    @ManyToMany
    @JoinTable(
            name = "groups_subjects",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Group> groups;


    public Subject() {
    }

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }
}
