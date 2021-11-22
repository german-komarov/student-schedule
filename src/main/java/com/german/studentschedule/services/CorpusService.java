package com.german.studentschedule.services;

import com.german.studentschedule.domain.Corpus;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.repository.CorpusRepository;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
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

    public List<Corpus> readByNameContaining(String word) throws NotFoundException {
        return this.repository.findByNameContainingInLowerCase(word.toLowerCase(Locale.ROOT));
    }

    public Corpus create(String name) throws AlreadyExistsException {
        boolean exists = this.repository.existsByName(name);
        if(exists) {
            throw new AlreadyExistsException(String.format("There is already corpus with name = %s", name));
        }
        return this.repository.save(new Corpus(name));
    }

    public Corpus update(Long id, String updatedName) throws NotFoundException {
        Corpus corpus = this.readById(id);
        corpus.setName(updatedName);
        return this.repository.save(corpus);
    }

    public void delete(Long id) throws NotAllowedOperation {
        boolean used = this.repository.isUsed(id);
        if(used) {
            throw new NotAllowedOperation("This corpus cannot be deleted as it is used by auditory");
        }
        this.repository.deleteByIdRegardlessExistence(id);
    }

}
