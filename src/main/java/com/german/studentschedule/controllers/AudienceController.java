package com.german.studentschedule.controllers;


import com.german.studentschedule.domain.Audience;
import com.german.studentschedule.dto.AudienceDto;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.NotFoundException;
import com.german.studentschedule.services.AudienceService;
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
@RequestMapping("/api/v1/audiences")
public class AudienceController {

    private static final Logger log = LoggerFactory.getLogger(AudienceController.class);

    private final AudienceService audienceService;

    @Autowired
    public AudienceController(AudienceService audienceService) {
        this.audienceService = audienceService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        try {
            List<AudienceDto> audiences = this.audienceService.readAllWithAllNests().stream().map(AudienceDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("audiences", audiences));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            Audience audience = this.audienceService.readByIdWithAllNests(id);
            return ResponseEntity.ok(singletonMap("audience", new AudienceDto(audience)));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @GetMapping("/by/corpus/{corpusId}")
    public ResponseEntity<Object> getByCorpus(@PathVariable Long corpusId) {
        try {
            List<AudienceDto> audiences = this.audienceService.readByCorpusWithAllNests(corpusId).stream().map(AudienceDto::new).collect(Collectors.toList());
            return ResponseEntity.ok(singletonMap("audiences", audiences));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @PostMapping
    public ResponseEntity<Object> post(@RequestParam Long corpusId, @RequestParam int room) {
        try {
            Audience audience = this.audienceService.create(corpusId, room);
            return ResponseEntity.status(201).body(singletonMap("audience", new AudienceDto(audience)));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> put(@PathVariable Long id, @RequestParam int room) {
        try {
            Audience audience = this.audienceService.update(id, room);
            return ResponseEntity.ok(singletonMap("audience", new AudienceDto(audience)));
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
            this.audienceService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotAllowedOperation e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }
}
