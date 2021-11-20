package com.german.studentschedule.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "corpuses")
public class Corpus extends BaseModel {

    private String name;

    public Corpus() {
    }

    public Corpus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
