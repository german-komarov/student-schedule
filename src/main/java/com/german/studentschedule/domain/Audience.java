package com.german.studentschedule.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "audiences",
        indexes = @Index(name = "uk__auditory__corpus__room", columnList = "corpus_id, room", unique = true)
)
public class Audience extends BaseModel {

    private int room;

    @ManyToOne(fetch = FetchType.LAZY)
    private Corpus corpus;


    public Audience() {
    }

    public Audience(Corpus corpus, int room) {
        this.corpus = corpus;
        this.room = room;
    }

    public Corpus getCorpus() {
        return corpus;
    }

    public void setCorpus(Corpus corpus) {
        this.corpus = corpus;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
