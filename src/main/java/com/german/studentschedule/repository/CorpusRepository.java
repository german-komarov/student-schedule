package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Corpus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CorpusRepository extends JpaRepository<Corpus, Long> {

    @Query("select c from Corpus c where c.name like %:word%")
    Optional<Corpus> findByNameContaining(String word);

    @Query("select case when count(a)>0 then true else false end from Auditory a where a.corpus.id=:corpus_id")
    boolean isUsed(@Param("corpus_id") Long id);

}
