package com.german.studentschedule.controllers;

import com.german.studentschedule.domain.Corpus;
import com.german.studentschedule.exceptions.AlreadyExistsException;
import com.german.studentschedule.services.CorpusService;
import com.german.studentschedule.exceptions.NotAllowedOperation;
import com.german.studentschedule.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.german.studentschedule.util.constants.Templates.SERVER_ERROR_JSON;
import static java.util.Collections.singletonMap;

@RestController
@RequestMapping("/api/v1/corpuses")
public class CorpusController {

    private static final Logger log = LoggerFactory.getLogger(CorpusController.class);

    private final CorpusService corpusService;


    @Autowired
    public CorpusController(CorpusService corpusService) {
        this.corpusService = corpusService;
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        try {
            List<Corpus> corpuses = this.corpusService.readAll();
            return ResponseEntity.ok(singletonMap("corpuses", corpuses));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }


    @GetMapping("/by/id/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        try {
            Corpus corpus = this.corpusService.readById(id);
            return ResponseEntity.ok(singletonMap("corpus", corpus));
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
            List<Corpus> corpuses = this.corpusService.readByNameContaining(word);
            return ResponseEntity.ok(singletonMap("corpuses", corpuses));
        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(404)
                    .body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestParam String name) {
        try {
            Corpus corpus = this.corpusService.create(name);
            return ResponseEntity.status(201).body(singletonMap("corpus", corpus));
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
            Corpus corpus = this.corpusService.update(id, name);
            return ResponseEntity.ok(singletonMap("corpus", corpus));
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
            this.corpusService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotAllowedOperation e) {
            return ResponseEntity.badRequest().body(singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().body(SERVER_ERROR_JSON);
        }
    }

}
