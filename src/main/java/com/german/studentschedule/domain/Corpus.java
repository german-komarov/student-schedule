package com.german.studentschedule.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "corpuses")
public class Corpus extends BaseModel {

    private String name;

    @OneToMany(mappedBy = "corpus")
    private Set<Audience> audiences;

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
