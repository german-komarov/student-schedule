package com.german.studentschedule.repository;

import com.german.studentschedule.domain.User;
import com.german.studentschedule.util.projections.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u.email as email, ur.name as roleName from User u join u.role ur")
    List<UserProjection> findAllProjections();

    @Query("select u from User u join u.role where u.email=:email")
    Optional<User> findByEmailWithRoles(String email);

}
