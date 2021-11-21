package com.german.studentschedule.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "auditories",
        indexes = @Index(name = "uk__auditory__corpus__room", columnList = "corpus_id, room_id", unique = true)
)
public class Auditory extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Corpus corpus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Room room;

    public Auditory() {
    }

    public Auditory(Corpus corpus, Room room) {
        this.corpus = corpus;
        this.room = room;
    }

    public Corpus getCorpus() {
        return corpus;
    }

    public void setCorpus(Corpus corpus) {
        this.corpus = corpus;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
