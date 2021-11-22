package com.german.studentschedule.dto;

import com.german.studentschedule.domain.User;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class FullUserDto {
    private Long id;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private String email;
    private boolean enable;
    private Set<RoleDto> roles;
    private ShortGroupDto group;
    private Set<LessonDto> lessons;

    public FullUserDto() {
    }


    public FullUserDto(User user) {
        if(user==null) {
            return;
        }
        this.id = user.getId();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.email = user.getEmail();
        this.enable = user.isEnabled();
        if(user.getRoles()!=null) {
            this.roles = user.getRoles().stream().map(RoleDto::new).collect(Collectors.toSet());
        }
        if (user.getGroup()!=null) {
            this.group = new ShortGroupDto(user.getGroup());
            if(user.getGroup().getLessons()!=null) {
                this.lessons = user.getGroup().getLessons().stream().map(LessonDto::new).collect(Collectors.toSet());
            }
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDto> roles) {
        this.roles = roles;
    }

    public ShortGroupDto getGroup() {
        return group;
    }

    public void setGroup(ShortGroupDto group) {
        this.group = group;
    }

    public Set<LessonDto> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonDto> lessons) {
        this.lessons = lessons;
    }
}