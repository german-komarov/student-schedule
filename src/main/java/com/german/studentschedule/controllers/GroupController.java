package com.german.studentschedule.controllers;

import com.german.studentschedule.domain.Group;
import com.german.studentschedule.dto.FullGroupDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.services.GroupService;
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
@RequestMapping("/api/v1/groups")
public class GroupController {

    private static final Logger log = LoggerFactory.getLogger(GroupController.class);

    private final GroupService groupService;


    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @GetMapping
    public ResponseEntity<Object> getAll() {
        try {
            List<FullGroupDto> groups = this.groupService.readAll().stream().map(FullGroupDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("groups", groups));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            Group group = this.groupService.readByIdWithAllNests(id);
            return ResponseEntity.ok(singletonMap("group", new FullGroupDto(group)));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/name/{word}")
    public ResponseEntity<Object> getByNameContaining(@PathVariable String word) {
        try {
            List<FullGroupDto> groups = this.groupService.readByNameContaining(word).stream().map(FullGroupDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("groups", groups));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestParam String name) {
        try {
            Group group = this.groupService.create(name);
            return ResponseEntity.status(201).body(singletonMap("group", new FullGroupDto(group)));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @RequestParam String name) {
        try {
            Group group = this.groupService.update(id, name);
            return ResponseEntity.ok(singletonMap("group", new FullGroupDto(group)));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            this.groupService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotAllowedOperation e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }
}
