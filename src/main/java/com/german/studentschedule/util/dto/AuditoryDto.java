package com.german.studentschedule.util.dto;

import com.german.studentschedule.domain.Auditory;

public class AuditoryDto {
    private String corpus;
    private int room;

    public AuditoryDto() {
    }

    public AuditoryDto(String corpus, int room) {
        this.corpus = corpus;
        this.room = room;
    }

    public AuditoryDto(Auditory auditory) {
        if(auditory==null) {
            return;
        }
        this.corpus = auditory.getCorpus().getName();
        this.room = auditory.getRoom();

    }

    public String getCorpus() {
        return corpus;
    }

    public void setCorpus(String corpus) {
        this.corpus = corpus;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }
}
