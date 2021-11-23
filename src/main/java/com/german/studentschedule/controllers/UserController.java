package com.german.studentschedule.controllers;


import com.german.studentschedule.domain.User;
import com.german.studentschedule.dto.IncomingUserDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.WrongDataException;
import com.german.studentschedule.services.UserService;
import com.german.studentschedule.dto.FullUserDto;
import com.german.studentschedule.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<Object> getAll() {
        try {
            List<FullUserDto> users = this.userService.readAllWithAllNests().stream().map(FullUserDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("users", users));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/self")
    public ResponseEntity<Object> getSelf(@AuthenticationPrincipal User principal) {
        try {
            return ResponseEntity.ok(new FullUserDto(this.userService.readById(principal.getId())));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            User user = this.userService.readByIdWithAllNests(id);
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
            User user = this.userService.readByEmailWithAllNests(email);
            return ResponseEntity.ok(singletonMap("user", new FullUserDto(user)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @PostMapping
    public ResponseEntity<Object> post(@AuthenticationPrincipal User principal, IncomingUserDto userDto) {
        try {
            User user = this.userService.create(principal, userDto);
            return ResponseEntity.status(201).body(singletonMap("user", new FullUserDto(user)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (WrongDataException | AlreadyExistsException | NotAllowedOperation e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@AuthenticationPrincipal User principal, @PathVariable Long id, IncomingUserDto userDto) {
        try {
            User user = this.userService.update(principal, id, userDto);
            return ResponseEntity.ok(singletonMap("user", new FullUserDto(user)));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(singletonMap("message", e.getMessage()));
        } catch (WrongDataException | AlreadyExistsException | NotAllowedOperation e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@AuthenticationPrincipal User principal, @PathVariable Long id) {
        try {
            this.userService.delete(principal, id);
            return ResponseEntity.noContent().build();
        } catch (NotAllowedOperation e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }
}
