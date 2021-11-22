package com.german.studentschedule.services;


import com.german.studentschedule.domain.Audience;
import com.german.studentschedule.domain.Corpus;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.repository.AudienceRepository;
import com.german.studentschedule.dto.AudienceDto;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class AudienceService {

    private final AudienceRepository repository;
    private final CorpusService corpusService;

    @Autowired
    public AudienceService(AudienceRepository repository, CorpusService corpusService) {
        this.repository = repository;
        this.corpusService = corpusService;
    }


    public List<Audience> readAll() {
        return this.repository.findAllCustom();
    }


    public Audience readById(Long id) throws NotFoundException {
        Optional<Audience> optionalAudience = this.repository.findByIdCustom(id);
        if(optionalAudience.isPresent()) {
            return optionalAudience.get();
        }
        throw new NotFoundException(String.format("There is no audience with id=%d", id));
    }


    public List<Audience> readByCorpus(Long corpusId) {
        return this.repository.findByCorpus(corpusId);
    }


    public Audience create(Long corpusId, int room) throws NotFoundException, AlreadyExistsException {
        boolean exists = this.repository.existsByCorpusAndRoom(corpusId, room);
        if(exists) {
            throw new AlreadyExistsException(String.format("There is already audience with corpus.id = %d and room = %d", corpusId, room));
        }
        Corpus corpus = this.corpusService.readById(corpusId);
        Audience audience = new Audience(corpus, room);
        audience = this.repository.save(audience);
        return audience;
    }

    public Audience update(Long id, int updatedRoom) throws NotFoundException {
        Optional<Audience> optionalAudience = this.repository.findById(id);
        if(optionalAudience.isPresent()) {
            Audience audience = optionalAudience.get();
            audience.setRoom(updatedRoom);
            audience = this.repository.save(audience);
            return audience;
        }
        throw new NotFoundException(String.format("There is no audience with id = %d", id));
    }

    public void delete(Long id) throws NotAllowedOperation {
        boolean used = this.repository.isUsed(id);
        if(used) {
            throw new NotAllowedOperation("This auditory cannot be deleted as it is used by lesson");
        }
        this.repository.deleteById(id);
    }

}
