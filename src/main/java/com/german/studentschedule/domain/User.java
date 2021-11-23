package com.german.studentschedule.domain;

import com.german.studentschedule.util.constants.RoleName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;


@Entity
@Table(name = "users")
public class User extends BaseModel implements UserDetails {

    private String email;
    private String password;
    private boolean enabled;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleName role;


    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(()->this.role.toString());
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public RoleName getRole() {
        return role;
    }

    public void setRole(RoleName role) {
        this.role = role;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
