package com.german.studentschedule.controllers;

import com.german.studentschedule.domain.Subject;
import com.german.studentschedule.dto.FullSubjectDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.services.SubjectService;
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
@RequestMapping("/api/v1/subjects")
public class SubjectController {

    private static final Logger log = LoggerFactory.getLogger(SubjectController.class);

    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        try {
            List<FullSubjectDto> subjects = this.subjectService.readAll().stream().map(FullSubjectDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("subjects", subjects));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            Subject subject = this.subjectService.readById(id);
            return ResponseEntity.ok(singletonMap("subject", new FullSubjectDto(subject)));
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
            List<FullSubjectDto> subjects = this.subjectService.readByNameContaining(word).stream().map(FullSubjectDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("subjects", subjects));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @PostMapping
    public ResponseEntity<Object> post(@RequestParam String name) {
        try {
            Subject subject = this.subjectService.create(name);
            return ResponseEntity.status(201).body(singletonMap("subject", new FullSubjectDto(subject)));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, String name) {
        try {
            Subject subject = this.subjectService.update(id, name);
            return ResponseEntity.ok(singletonMap("subject", subject));
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
            this.subjectService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotAllowedOperation e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

}
