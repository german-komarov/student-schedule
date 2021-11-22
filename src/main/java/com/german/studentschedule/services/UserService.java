package com.german.studentschedule.services;


import com.german.studentschedule.domain.Group;
import com.german.studentschedule.domain.User;
import com.german.studentschedule.dto.CreatingUserDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.WrongDataException;
import com.german.studentschedule.repository.UserRepository;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.util.constants.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository repository;
    private final GroupService groupService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository repository,
                       GroupService groupService,
                       @Lazy BCryptPasswordEncoder passwordEncoder,
                       RoleService roleService) {
        this.repository = repository;
        this.groupService = groupService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    public List<User> readAllWithAllProperties() {
        return this.repository.findAllWithAllProperties();
    }

    public User readByIdWithAllProperties(Long id) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByIdWithAllProperties(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with id = %d", id));
    }

    public User readByEmail(String email) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByEmailWithAllProperties(email);
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

    public User create(CreatingUserDto userDto) throws AlreadyExistsException, WrongDataException, NotFoundException {
        User user = this.initialize(userDto);
        return this.repository.save(user);
    }


    private User initialize(CreatingUserDto userDto) throws AlreadyExistsException, WrongDataException, NotFoundException {
        String email = userDto.getEmail();
        if(this.repository.existsByEmail(email)) {
            throw new AlreadyExistsException(String.format("There is already user with email = %s", email));
        }

        String password = userDto.getPassword();
        if(password==null) {
            throw new WrongDataException("Password cannot be null");
        }
        String passwordConfirm = userDto.getPasswordConfirm();
        if(!password.equals(passwordConfirm)) {
            throw new WrongDataException("Password and password confirm are not matched");
        }

        RoleName roleName;

        try {
            roleName = RoleName.valueOf(userDto.getRole());
        } catch (IllegalArgumentException e) {
            throw new WrongDataException(String.format("There is no role with name = %s. Only %s available", userDto.getRole(), Arrays.toString(RoleName.values())));
        }
        Group group = null;
        if(userDto.getGroup()!=null) {
            group = this.groupService.readById(userDto.getGroup());
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(this.passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRoles(Collections.singleton(this.roleService.readByName(roleName)));
        user.setGroup(group);
        return user;
    }

}
