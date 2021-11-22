package com.german.studentschedule.dto;

import com.german.studentschedule.domain.Group;

public class ShortGroupDto {

    private Long id;
    private String name;

    public ShortGroupDto() {
    }

    public ShortGroupDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ShortGroupDto(Group group) {
        if (group==null) {
            return;
        }
        this.id = group.getId();
        this.name = group.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
