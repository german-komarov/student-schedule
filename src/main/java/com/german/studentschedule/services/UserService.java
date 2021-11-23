package com.german.studentschedule.services;


import com.german.studentschedule.domain.Group;
import com.german.studentschedule.domain.User;
import com.german.studentschedule.dto.IncomingUserDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotAllowedOperation;
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
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    private final UserRepository repository;
    private final GroupService groupService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository,
                       GroupService groupService,
                       @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.groupService = groupService;
        this.passwordEncoder = passwordEncoder;
    }

    public User readById(Long id) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findById(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with id = %d", id));
    }

    public List<User> readAllWithAllNests() {
        return this.repository.findAllWithAllNests();
    }

    public User readByIdWithAllNests(Long id) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByIdWithAllNests(id);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with id = %d", id));
    }

    public User readByEmailWithAllNests(String email) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByEmailWithAllNests(email);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with email = %s", email));
    }

    public User readByEmail(String email) throws NotFoundException {
        Optional<User> optionalUser = this.repository.findByEmail(email);
        if(optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new NotFoundException(String.format("There is no user with email = %s", email));
    }

    public User create(User principal, IncomingUserDto dto) throws AlreadyExistsException, WrongDataException, NotFoundException, NotAllowedOperation {
        this.validateForCreating(principal, dto);
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setRole(RoleName.valueOf(dto.getRole()));
        if(dto.getGroup()!=null) {
            Group group = this.groupService.readByIdWithAllNests(dto.getGroup());
            user.setGroup(group);
        }
        return this.repository.save(user);
    }


    public User update(User principal, Long id, IncomingUserDto dto) throws NotFoundException, AlreadyExistsException, WrongDataException, NotAllowedOperation {
        User userToUpdate = this.readByIdWithAllNests(id);
        this.validateForUpdating(principal, userToUpdate, dto);
        if(dto.getEmail()!=null) {
            userToUpdate.setEmail(dto.getEmail());
        }
        if(dto.getPassword()!=null) {
            userToUpdate.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        }
        if(dto.getRole()!=null) {
            userToUpdate.setRole(RoleName.valueOf(dto.getRole()));
        }
        if(dto.getGroup()!=null) {
            Group group = this.groupService.readByIdWithAllNests(dto.getGroup());
            userToUpdate.setGroup(group);
        }
        return this.repository.save(userToUpdate);
    }


    public void delete(User principal, Long id) throws NotAllowedOperation {
        if(principal.getId().equals(id)) {
            throw new NotAllowedOperation("It is not allowed to delete yourself");
        }
        User userToDelete;
        try {
            userToDelete = this.readById(id);
        } catch (NotFoundException e) {
            return;
        }
        if(principal.getRole().equals(RoleName.ROLE_ADMIN) && !userToDelete.getRole().equals(RoleName.ROLE_STUDENT)) {
            throw new NotAllowedOperation("ADMIN can delete only students");
        }
        if(userToDelete.getRole().equals(RoleName.ROLE_SUPER_ADMIN)) {
            throw new NotAllowedOperation("It is not allowed to delete SUPER ADMIN via API, only manually");
        }
        this.repository.deleteByIdCustom(id);
    }

    private void validateForCreating(User principal, IncomingUserDto dto) throws AlreadyExistsException, WrongDataException, NotAllowedOperation {
        String email = dto.getEmail();
        if(this.repository.existsByEmail(email)) {
            throw new AlreadyExistsException(String.format("There is already user with email = %s", email));
        }

        String password = dto.getPassword();
        if(password==null) {
            throw new WrongDataException("Password cannot be null");
        }
        String passwordConfirm = dto.getPasswordConfirm();
        if(!password.equals(passwordConfirm)) {
            throw new WrongDataException("Password and password confirm are not matched");
        }


        if(dto.getRole()==null) {
            throw new WrongDataException("Role cannot be null");
        }
        RoleName roleName;
        try {
            roleName = RoleName.valueOf(dto.getRole());
        } catch (IllegalArgumentException e) {
            throw new WrongDataException(String.format("There is no role with name = %s. Only %s available", dto.getRole(), Arrays.toString(new RoleName[]{RoleName.ROLE_STUDENT, RoleName.ROLE_ADMIN})));
        }
        if(principal.getRole().equals(RoleName.ROLE_ADMIN) && !roleName.equals(RoleName.ROLE_STUDENT)) {
            throw new NotAllowedOperation("ADMIN can create only students");
        }
        if(roleName.equals(RoleName.ROLE_SUPER_ADMIN)) {
            throw new NotAllowedOperation("It is impossible to create SUPER ADMIN vie API, only manually");
        }
    }
    private void validateForUpdating(User principal, User userToUpdate, IncomingUserDto dto) throws AlreadyExistsException, WrongDataException, NotAllowedOperation {
        String email = dto.getEmail();
        if(email!=null) {
            if (!userToUpdate.getEmail().equals(email)) {
                if (this.repository.existsByEmail(email)) {
                    throw new AlreadyExistsException(String.format("Cannot be updated to email = %s, as it is already taken by another user", email));
                }
            }
        }
        String password = dto.getPassword();
        if(password!=null) {
                if(!password.equals(dto.getPasswordConfirm())) {
                    throw new WrongDataException("Password and password confirm are not matched");
                }
        }
        if(dto.getRole()!=null) {
            RoleName roleName;
            try {
                roleName = RoleName.valueOf(dto.getRole());
            } catch (IllegalArgumentException e) {
                throw new WrongDataException(String.format("There is no role with name = %s. Only %s available", dto.getRole(), Arrays.toString(RoleName.values())));
            }
            if(principal.getRole().equals(RoleName.ROLE_ADMIN) && !roleName.equals(RoleName.ROLE_STUDENT)) {
                throw new NotAllowedOperation("ADMIN can update only students");
            }
            if(roleName.equals(RoleName.ROLE_SUPER_ADMIN)) {
                throw new NotAllowedOperation("It is impossible to update SUPER ADMIN vie API, only manually");
            }
        }
    }
}
