package com.german.studentschedule.repository;

import com.german.studentschedule.domain.Corpus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CorpusRepository extends JpaRepository<Corpus, Long> {
}
