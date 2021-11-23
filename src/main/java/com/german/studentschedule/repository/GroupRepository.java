package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query("select case when count(g.id)>0 then true else false end from Group g where g.name=:name")
    boolean existsByName(String name);


    @Query("select g " +
            "from Group g " +
            "left join fetch g.subjects gsub " +
            "left join fetch g.lessons gl " +
            "left join fetch gl.subject gls " +
            "left join fetch g.students gstud " +
            "left join fetch gl.audience gla " +
            "left join fetch gla.corpus")
    List<Group> findAllWithAllNests();

    @Query("select g " +
            "from Group g " +
            "left join fetch g.subjects gsub " +
            "left join fetch g.lessons gl " +
            "left join fetch gl.subject gls " +
            "left join fetch g.students gstud " +
            "left join fetch gl.audience gla " +
            "left join fetch gla.corpus " +
            "where g.id=:id")
    Optional<Group> findByIdWithAllNests(Long id);


    @Query("select g from Group g left join fetch g.students where g.id=id")
    Optional<Group> findByIdWithStudents(Long id);

    @Query("select g " +
            "from Group g " +
            "left join fetch g.subjects gsub " +
            "left join fetch g.lessons gl " +
            "left join fetch gl.subject gls " +
            "left join fetch g.students gstud " +
            "left join fetch gl.audience gla " +
            "left join fetch gla.corpus " +
            "where lower(g.name) like %:word%")
    List<Group> findByNameContainingWithAllNests(String word);




    @Query("select case when count(u.id)>0 then true else false end from User u where u.group.id=:id")
    boolean isUsed(Long id);

    @Modifying(flushAutomatically = true)
    @Query("delete from Group g where g.id = :id")
    void deleteByIdCustom(Long id);
}
