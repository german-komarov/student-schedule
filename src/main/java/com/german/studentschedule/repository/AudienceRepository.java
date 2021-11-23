package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Audience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AudienceRepository extends JpaRepository<Audience, Long> {

    @Query("select a from Audience a join fetch a.corpus ac")
    List<Audience> findAllWithAllNests();

    @Query("select a from Audience a join fetch a.corpus ac where a.id=:id")
    Optional<Audience> findByIdWithAllNests(Long id);

    @Query("select a from Audience a join fetch a.corpus ac where ac.id=:corpus_id")
    List<Audience> findByCorpusWithAllNests(@Param("corpus_id") Long corpusId);

    @Query("select case when count(a.id)>0 then true else false end from Audience a where a.corpus.id=:corpus_id and a.room=:room")
    boolean existsByCorpusAndRoom(@Param("corpus_id") Long corpus, int room);

    @Query("select case when count(l.id)>0 then true else false end from Lesson l where l.audience.id=:id")
    boolean isUsed(Long id);


    @Modifying(flushAutomatically = true)
    @Query("delete from Audience a where a.id = :id")
    void deleteByIdCustom(Long id);
}
