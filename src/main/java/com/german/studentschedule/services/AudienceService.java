package com.german.studentschedule.services;


import com.german.studentschedule.domain.Audience;
import com.german.studentschedule.domain.Corpus;
import com.german.studentschedule.repository.AudienceRepository;
import com.german.studentschedule.util.dto.AudienceDto;
import com.german.studentschedule.util.exceptions.NotAllowedOperation;
import com.german.studentschedule.util.exceptions.NotFoundException;
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


    public List<AudienceDto> readAll() {
        List<Audience> audiences = this.repository.findAllCustom();
        return audiences.stream().map(AudienceDto::new).collect(Collectors.toList());
    }


    public AudienceDto readById(Long id) throws NotFoundException {
        Optional<Audience> optionalAudience = this.repository.findByIdCustom(id);
        if(optionalAudience.isPresent()) {
            return new AudienceDto(optionalAudience.get());
        }
        throw new NotFoundException(String.format("There is no audience with id=%d", id));
    }


    public List<AudienceDto> readByCorpus(Long corpusId) {
        List<Audience> audiences = this.repository.findByCorpus(corpusId);
        return audiences.stream().map(AudienceDto::new).collect(Collectors.toList());
    }


    public AudienceDto create(Long corpusId, int room) throws NotFoundException, NotAllowedOperation {
        if(this.repository.existsByCorpusAndRoom(corpusId, room)) {
            throw new NotAllowedOperation(String.format("There is already audience with corpus.id = %d and room = %d", corpusId, room));
        }
        Corpus corpus = this.corpusService.readById(corpusId);
        Audience audience = new Audience(corpus, room);
        audience = this.repository.save(audience);
        return new AudienceDto(audience);
    }

    public AudienceDto update(Long id, int updatedRoom) throws NotFoundException {
        Optional<Audience> optionalAudience = this.repository.findByIdCustom(id);
        if(optionalAudience.isPresent()) {
            Audience audience = optionalAudience.get();
            audience.setRoom(updatedRoom);
            audience = this.repository.save(audience);
            return new AudienceDto(audience);
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
