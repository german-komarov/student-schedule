package com.german.studentschedule.domain;

import javax.persistence.*;
import java.time.ZonedDateTime;

@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected ZonedDateTime createdAt;
    protected ZonedDateTime updatedAt;


    @PrePersist
    private void onPersist() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
}
