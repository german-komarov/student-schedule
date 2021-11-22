package com.german.studentschedule.repository;

import com.german.studentschedule.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;



public interface UserRepository extends JpaRepository<User,Long> {


    @Query("select u " +
            "from User u " +
            "join fetch u.role ur " +
            "left join fetch u.group ug " +
            "left join fetch ug.lessons ugl " +
            "left join fetch ugl.subject ugls " +
            "left join fetch ugl.audience ugla " +
            "left join fetch ugl.group uglg " +
            "left join fetch ugla.corpus uglac")
    List<User> findAllCustom();


    @Query("select u " +
            "from User u " +
            "join fetch u.role ur " +
            "left join fetch u.group ug " +
            "left join fetch ug.lessons ugl " +
            "left join fetch ugl.subject ugls " +
            "left join fetch ugl.audience ugla " +
            "left join fetch ugl.group uglg " +
            "left join fetch ugla.corpus uglac " +
            "where u.id = :id")
    Optional<User> findByIdCustom(Long id);



    @Query("select u " +
            "from User u " +
            "join fetch u.role ur " +
            "left join fetch u.group ug " +
            "left join fetch ug.lessons ugl " +
            "left join fetch ugl.subject ugls " +
            "left join fetch ugl.audience ugla " +
            "left join fetch ugl.group uglg " +
            "left join fetch ugla.corpus uglac " +
            "where u.email = :email")
    Optional<User> findByEmailCustom(String email);


    @Query("select u from User u join fetch u.role where u.email=:email")
    Optional<User> findByEmailWithRole(String email);

}
