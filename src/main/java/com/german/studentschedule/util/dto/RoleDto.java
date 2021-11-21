package com.german.studentschedule.util.dto;

import com.german.studentschedule.domain.Role;

public class RoleDto {

    private Long id;
    private String name;

    public RoleDto() {
    }

    public RoleDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDto(Role role) {
        if(role==null) {
            return;
        }
        this.id = role.getId();
        this.name = role.getAuthority();
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
