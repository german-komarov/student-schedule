package com.german.studentschedule.dto;

import com.german.studentschedule.domain.Audience;

import java.time.ZonedDateTime;

public class AudienceDto {
    private Long id;
    private String corpus;
    private int room;
    private ZonedDateTime createAt;
    private ZonedDateTime updatedAt;

    public AudienceDto() {
    }

    public AudienceDto(Long id, String corpus, int room, ZonedDateTime createAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.corpus = corpus;
        this.room = room;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public AudienceDto(Audience audience) {
        if(audience ==null) {
            return;
        }
        this.id = audience.getId();
        this.corpus = audience.getCorpus().getName();
        this.room = audience.getRoom();
        this.createAt = audience.getCreatedAt();
        this.updatedAt = audience.getUpdatedAt();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ZonedDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(ZonedDateTime createAt) {
        this.createAt = createAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
