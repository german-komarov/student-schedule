package com.german.studentschedule.services;


import com.german.studentschedule.domain.User;
import com.german.studentschedule.repository.UserRepository;
import com.german.studentschedule.dto.UserDto;
import com.german.studentschedule.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository repository;


    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<User> readAll() {
        return this.repository.findAllCustom();
    }

    public User readById(Long id) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByIdCustom(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with id = %d", id));
    }

    public User readByEmail(String email) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByEmailCustom(email);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with email = %s", email));
    }

    public User readByEmailWithRole(String email) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByEmailWithRole(email);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with email = %s", email));
    }
}
