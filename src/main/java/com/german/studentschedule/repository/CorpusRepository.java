package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Corpus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CorpusRepository extends JpaRepository<Corpus, Long> {

    @Query("select case when count(c.id)>0 then true else false end from Corpus c where c.name=:name")
    boolean existsByName(String name);

    @Query("select c from Corpus c where lower(c.name) like %:word%")
    List<Corpus> findByNameContainingInLowerCase(String word);

    @Query("select case when count(a.id)>0 then true else false end from Audience a where a.corpus.id=:corpus_id")
    boolean isUsed(@Param("corpus_id") Long id);


    @Modifying(flushAutomatically = true)
    @Query("delete from Corpus c where c.id = :id")
    void deleteByIdRegardlessExistence(Long id);
}
