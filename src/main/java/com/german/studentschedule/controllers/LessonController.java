package com.german.studentschedule.controllers;


import com.german.studentschedule.domain.Lesson;
import com.german.studentschedule.domain.User;
import com.german.studentschedule.dto.IncomingLessonDto;
import com.german.studentschedule.dto.LessonDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.exceptions.WrongValueException;
import com.german.studentschedule.services.LessonService;
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
@RequestMapping("/api/v1/lessons")
public class LessonController {

    private static final Logger log = LoggerFactory.getLogger(LessonController.class);

    private final LessonService lessonService;


    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }


    @GetMapping
    public ResponseEntity<Object> getAll() {
        try {
            List<LessonDto> lessons = this.lessonService.readAllWithAllNests().stream().map(LessonDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("lessons", lessons));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            Lesson lesson = this.lessonService.readByIdWithAllNests(id);
            return ResponseEntity.ok(singletonMap("lesson", new LessonDto(lesson)));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @GetMapping("/by/date/{date}")
    public ResponseEntity<Object> getById(@PathVariable String date) {
        try {
            List<LessonDto> lessons = this.lessonService.readByDateWithAllNests(date).stream().map(LessonDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("lessons", lessons));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (WrongValueException e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }



    @GetMapping("/by/date/{date}/for/principal")
    public ResponseEntity<Object> getByIdForPrincipal(@AuthenticationPrincipal User principal, @PathVariable String date) {
        try {
            List<LessonDto> lessons = this.lessonService.readByDateForPrincipalWithAllNests(principal, date).stream().map(LessonDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("lessons", lessons));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (WrongValueException e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }




    @PostMapping
    public ResponseEntity<Object> post(IncomingLessonDto lessonDto) {
        try {
            Lesson lesson = this.lessonService.create(lessonDto);
            return ResponseEntity.ok(singletonMap("lesson", new LessonDto(lesson)));
        } catch (WrongValueException e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, IncomingLessonDto lessonDto) {
        try {
            Lesson lesson = this.lessonService.update(id, lessonDto);
            return ResponseEntity.ok(singletonMap("lesson", new LessonDto(lesson)));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (WrongValueException | AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            this.lessonService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

}
