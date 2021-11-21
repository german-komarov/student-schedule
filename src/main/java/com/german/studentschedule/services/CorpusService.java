package com.german.studentschedule.services;

import com.german.studentschedule.domain.Corpus;
import com.german.studentschedule.repository.CorpusRepository;
import com.german.studentschedule.util.exceptions.NotAllowedOperation;
import com.german.studentschedule.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class CorpusService {

    private final CorpusRepository repository;

    @Autowired
    public CorpusService(CorpusRepository repository) {
        this.repository = repository;
    }


    public List<Corpus> readAll() {
         return this.repository.findAll();
    }

    public Corpus readById(Long id) throws NotFoundException {
        Optional<Corpus> optionalCorpus = this.repository.findById(id);
        if(optionalCorpus.isPresent()) {
            return optionalCorpus.get();
        }
        throw new NotFoundException(String.format("There is no corpus with id = %d", id));
    }

    public Corpus readByNameContaining(String word) throws NotFoundException {
        Optional<Corpus> optionalCorpus = this.repository.findByNameContaining(word);
        if(optionalCorpus.isPresent()) {
            return optionalCorpus.get();
        }
        throw new NotFoundException(String.format("There is no corpus containing word = %s", word));
    }

    public Corpus create(String name) {
        return this.repository.save(new Corpus(name));
    }

    public Corpus update(Long id, String name) throws NotFoundException {
        Corpus corpus = this.readById(id);
        corpus.setName(name);
        return this.repository.save(corpus);
    }

    public void delete(Long id) throws NotAllowedOperation {
        boolean used = this.repository.isUsed(id);
        if(used) {
            throw new NotAllowedOperation("This corpus cannot be deleted as it is used by auditory");
        }
        this.repository.deleteById(id);
    }

}
