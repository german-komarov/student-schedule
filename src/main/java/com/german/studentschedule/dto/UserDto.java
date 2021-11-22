package com.german.studentschedule.dto;

import com.german.studentschedule.domain.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserDto {

    private String email;
    private boolean enable;
    private RoleDto role;
    private ShortGroupDto group;
    private Set<LessonDto> lessons;

    public UserDto() {
    }

    public UserDto(String email, boolean enable, RoleDto role, ShortGroupDto group, Set<LessonDto> lessons) {
        this.email = email;
        this.enable = enable;
        this.role = role;
        this.group = group;
        this.lessons = lessons;
    }


    public UserDto(User user) {
        if(user==null) {
            return;
        }
        this.email = user.getEmail();
        this.enable = user.isEnabled();
        this.role = new RoleDto(user.getRole());
        if (user.getGroup()!=null) {
            this.group = new ShortGroupDto(user.getGroup());
            if(user.getGroup().getLessons()!=null) {
                this.lessons = user.getGroup().getLessons().stream().map(LessonDto::new).collect(Collectors.toSet());
            }
        }

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

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
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
