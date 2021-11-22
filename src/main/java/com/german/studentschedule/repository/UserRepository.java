package com.german.studentschedule.repository;

import com.german.studentschedule.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select case when count(u.id)>0 then true else false end from User u where u.email=:email")
    boolean existsByEmail(String email);


    @Query("select u " +
            "from User u " +
            "join fetch u.roles ur " +
            "left join fetch u.group ug " +
            "left join fetch ug.lessons ugl " +
            "left join fetch ugl.subject ugls " +
            "left join fetch ugl.audience ugla " +
            "left join fetch ugl.group uglg " +
            "left join fetch ugla.corpus uglac")
    List<User> findAllWithAllProperties();


    @Query("select u " +
            "from User u " +
            "join fetch u.roles ur " +
            "left join fetch u.group ug " +
            "left join fetch ug.lessons ugl " +
            "left join fetch ugl.subject ugls " +
            "left join fetch ugl.audience ugla " +
            "left join fetch ugl.group uglg " +
            "left join fetch ugla.corpus uglac " +
            "where u.id = :id")
    Optional<User> findByIdWithAllProperties(Long id);



    @Query("select u " +
            "from User u " +
            "join fetch u.roles ur " +
            "left join fetch u.group ug " +
            "left join fetch ug.lessons ugl " +
            "left join fetch ugl.subject ugls " +
            "left join fetch ugl.audience ugla " +
            "left join fetch ugl.group uglg " +
            "left join fetch ugla.corpus uglac " +
            "where u.email = :email")
    Optional<User> findByEmailWithAllProperties(String email);


    @Query("select u from User u join fetch u.roles where u.email=:email")
    Optional<User> findByEmailWithRole(String email);

}
