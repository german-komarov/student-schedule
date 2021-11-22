package com.german.studentschedule.dto;

import com.german.studentschedule.domain.User;

public class ShortUserDto {
    private Long id;
    private String email;

    public ShortUserDto() {
    }

    public ShortUserDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
