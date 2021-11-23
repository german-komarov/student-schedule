package com.german.studentschedule.services;

import com.german.studentschedule.domain.Subject;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SubjectService {

    private final SubjectRepository repository;


    @Autowired
    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public List<Subject> readAll() {
        return this.repository.findAllWithAllNests();
    }

    public Subject readByIdWithAllNests(Long id) throws NotFoundException {
        Optional<Subject> optionalSubject = this.repository.findByIdWithAllNests(id);
        if(optionalSubject.isPresent()) {
            return optionalSubject.get();
        }
        throw new NotFoundException(String.format("There is no subject with id = %d", id));
    }

    public List<Subject> readByNameContainingWithAllNests(String word) {
        return this.repository.findByNameContainingWithAllNests(word.toLowerCase(Locale.ROOT));
    }

    public Subject create(String name) throws AlreadyExistsException {
        boolean exists = this.repository.existsByName(name);
        if(exists) {
            throw new AlreadyExistsException(String.format("There is also subject with name = %s", name));
        }
        return this.repository.save(new Subject(name));
    }

    public Subject update(Long id, String updatedName) throws NotFoundException {
        Optional<Subject> optionalSubject = this.repository.findById(id);
        if(optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            subject.setName(updatedName);
            return subject;
        }
        throw new NotFoundException(String.format("There is no subject with id = %d", id));
    }

    public void delete(Long id) throws NotAllowedOperation {
        boolean used = this.repository.isUsed(id);
        if(used) {
            throw new NotAllowedOperation("This subject cannot be deleted as it is used by group(s)");
        }
        this.repository.deleteByIdCustom(id);
    }

}
