package com.german.studentschedule.controllers;


import com.german.studentschedule.services.AudienceService;
import com.german.studentschedule.util.dto.AudienceDto;
import com.german.studentschedule.util.exceptions.NotAllowedOperation;
import com.german.studentschedule.util.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.german.studentschedule.util.constants.Templates.SERVER_ERROR_JSON;
import static java.util.Collections.singleton;
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
            List<AudienceDto> audiences = this.audienceService.readAll();
            return ResponseEntity.ok(singletonMap("audiences", audiences));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            AudienceDto audience = this.audienceService.readById(id);
            return ResponseEntity.ok(singletonMap("audience", audience));
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
            List<AudienceDto> audiences = this.audienceService.readByCorpus(corpusId);
            return ResponseEntity.ok(singletonMap("audiences", audiences));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @PostMapping
    public ResponseEntity<Object> post(@RequestParam Long corpusId, @RequestParam int room) {
        try {
            AudienceDto audience = this.audienceService.create(corpusId, room);
            return ResponseEntity.ok(singletonMap("audience", audience));
        } catch (NotAllowedOperation e) {
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
}
