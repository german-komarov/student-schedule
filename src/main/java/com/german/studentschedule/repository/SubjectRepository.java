package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("select case when count(s.id)>0 then true else false end from Subject s where s.name=:name")
    boolean existsByName(String name);

    @Query("select s from Subject s join fetch s.lessons join fetch s.groups where s.id=:id")
    Optional<Subject> findByIdCustom(Long id);

    @Query("select s from Subject s join fetch s.lessons join fetch s.groups where s.name like %:word%")
    List<Subject> findByNameContaining(String word);

    @Query(value = "select exists(select * from groups_subjects where subject_id=:id)", nativeQuery = true)
    boolean isUsed(Long id);
}
