package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
