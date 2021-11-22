package com.german.studentschedule.controllers;


import com.german.studentschedule.domain.User;
import com.german.studentschedule.dto.CreatingUserDto;
import com.german.studentschedule.dto.ShortUserDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.WrongDataException;
import com.german.studentschedule.services.UserService;
import com.german.studentschedule.dto.FullUserDto;
import com.german.studentschedule.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.german.studentschedule.util.constants.Templates.SERVER_ERROR_JSON;
import static java.util.Collections.singletonMap;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    private ResponseEntity<Object> getAll() {
        try {
            List<FullUserDto> users = this.userService.readAllWithAllProperties().stream().map(FullUserDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("users", users));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            User user = this.userService.readByIdWithAllProperties(id);
            return ResponseEntity.ok(singletonMap("user", new FullUserDto(user)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/email/{email}")
    public ResponseEntity<Object> getByEmail(@PathVariable String email) {
        try {
            User user = this.userService.readByEmail(email);
            return ResponseEntity.ok(singletonMap("user", new FullUserDto(user)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @PostMapping
    public ResponseEntity<Object> post(CreatingUserDto userDto) {
        try {
            User user = this.userService.create(userDto);
            return ResponseEntity.ok(singletonMap("user", new FullUserDto(user)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (WrongDataException | AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        }
    }



}
