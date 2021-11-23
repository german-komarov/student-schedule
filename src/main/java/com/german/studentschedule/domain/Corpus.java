package com.german.studentschedule.domain;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Objects;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Corpus)) return false;
        Corpus corpus = (Corpus) o;
        return Objects.equals(getName(), corpus.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Corpus{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                '}';
    }
}
