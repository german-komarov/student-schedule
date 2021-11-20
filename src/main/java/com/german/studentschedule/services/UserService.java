package com.german.studentschedule.services;


import com.german.studentschedule.domain.User;
import com.german.studentschedule.repository.UserRepository;
import com.german.studentschedule.util.exceptions.NotFoundException;
import com.german.studentschedule.util.projections.UserProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository repository;


    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public List<UserProjection> readAllProjections() {
        return this.repository.findAllProjections();
    }

    public User readByEmailWithRole(String email) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByEmailWithRoles(email);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with email = %s", email));
    }
}
