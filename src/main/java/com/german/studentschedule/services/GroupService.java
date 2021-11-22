package com.german.studentschedule.services;

import com.german.studentschedule.domain.Group;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class GroupService {

    private final GroupRepository repository;

    @Autowired
    public GroupService(GroupRepository repository) {
        this.repository = repository;
    }

    public List<Group> readAll() {
        return this.repository.findAllWithAllProperties();
    }

    public Group readById(Long id) throws NotFoundException {
        Optional<Group> optionalGroup = this.repository.findById(id);
        if(optionalGroup.isPresent()) {
            return optionalGroup.get();
        }
        throw new NotFoundException(String.format("There is no group with id = %d", id));
    }

    public Group readByIdWithAllNests(Long id) throws NotFoundException {
        Optional<Group> optionalGroup = this.repository.findByIdWithAllProperties(id);
        if(optionalGroup.isPresent()) {
            return optionalGroup.get();
        }
        throw new NotFoundException(String.format("There is no group with id = %d", id));
    }

    public List<Group> readByNameContaining(String word) {
        return this.repository.findByNameContaining(word.toLowerCase(Locale.ROOT));
    }

    public Group create(String name) throws AlreadyExistsException {
        boolean exists = this.repository.existsByName(name);
        if(exists) {
            throw new AlreadyExistsException(String.format("There is already group with name = %s", name));
        }
        return this.repository.save(new Group(name));
    }

    public Group update(Long id, String updatedName) throws NotFoundException {
        Optional<Group> optionalGroup = this.repository.findById(id);
        if(optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            group.setName(updatedName);
            return this.repository.save(group);
        }
        throw new NotFoundException(String.format("There is no group with id = %d", id));
    }

    public void delete(Long id) throws NotAllowedOperation {
        boolean used = this.repository.isUsed(id);
        if(used) {
            throw new NotAllowedOperation("This group cannot be deleted as it is used by student(s)");
        }
        this.repository.deleteByIdRegardlessExistence(id);
    }
}
