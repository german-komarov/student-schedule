package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
