package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select l " +
            "from Lesson l " +
            "join fetch l.subject ls " +
            "join fetch l.group lg " +
            "join fetch l.audience la " +
            "join fetch la.corpus lac")
    List<Lesson> findAllWithAllNests();


    @Query("select l " +
            "from Lesson l " +
            "join fetch l.subject ls " +
            "join fetch l.group lg " +
            "join fetch l.audience la " +
            "join fetch la.corpus lac " +
            "where l.id=:id")
    Optional<Lesson> findByIdWithAllNests(Long id);


    @Query("select l " +
            "from Lesson l " +
            "join fetch l.subject ls " +
            "join fetch l.group lg " +
            "join fetch l.audience la " +
            "join fetch la.corpus lac " +
            "where l.date=:date")
    List<Lesson> findByDateWithAllNests(LocalDate date);

    @Query("select l " +
            "from Lesson l " +
            "join fetch l.subject ls " +
            "join fetch l.group lg " +
            "join fetch l.audience la " +
            "join fetch la.corpus lac " +
            "where l.date=:date and l.group.id=:groupId")
    List<Lesson> findByDateForPrincipalWithAllNests(LocalDate date, Long groupId);


    @Query("select case when count(l.id)>0 " +
            "then true else false end from Lesson l " +
            "where l.date=:date and l.subject.id=:subjectId and l.audience.id=:audienceId and l.group.id=:groupId")
    boolean existsByAllParameters(LocalDate date, Long subjectId, Long audienceId, Long groupId);


    @Modifying
    @Query("delete from Lesson l where l.id=:id")
    void deleteByIdCustom(Long id);

}
