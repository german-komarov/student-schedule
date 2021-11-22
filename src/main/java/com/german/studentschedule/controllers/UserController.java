package com.german.studentschedule.controllers;


import com.german.studentschedule.services.UserService;
import com.german.studentschedule.util.dto.UserDto;
import com.german.studentschedule.util.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
            List<UserDto> users = this.userService.readAll();
            return ResponseEntity.ok(singletonMap("users", users));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            UserDto userDto = this.userService.readById(id);
            return ResponseEntity.ok(singletonMap("user", userDto));
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
            UserDto userDto = this.userService.readByEmail(email);
            return ResponseEntity.ok(singletonMap("user", userDto));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }
}
