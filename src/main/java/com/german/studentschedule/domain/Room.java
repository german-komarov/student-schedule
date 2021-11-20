package com.german.studentschedule.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rooms")
public class Room extends BaseModel {

    private int number;

    public Room() {
    }

    public Room(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
